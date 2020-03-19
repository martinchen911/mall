package com.cf.mall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.bean.OmsCartItem;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.service.CartService;
import com.cf.mall.service.SkuService;
import com.cf.mall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author chen
 * @Date 2020/3/13
 */
@Controller
public class CartController {

    @Reference
    private SkuService skuService;

    @Reference
    private CartService cartService;

    @GetMapping("toTrade")
    @LoginRequired
    public String toTrade(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        String memberId = String.valueOf(request.getAttribute("memberId"));
        String nikeName = String.valueOf(request.getAttribute("nikeName"));

        return "toTrade";
    }

    @LoginRequired(loginSuccess=false)
    @PostMapping("checkCart")
    public String checkCart(OmsCartItem cartItem,ModelMap map,HttpServletRequest request) {

        String memberId = String.valueOf(request.getAttribute("memberId"));
        cartItem.setMemberId(Long.parseLong(memberId));
        cartService.checkedCart(cartItem);

        List<OmsCartItem> cartItems = cartService.listCart(memberId);
        map.put("cartList",cartItems);

        return "cartListInner";
    }

    @LoginRequired(loginSuccess=false)
    @RequestMapping("addToCart")
    public String addToCart(String skuId,Integer quantity,HttpServletRequest request, HttpServletResponse response) {
        PmsSkuInfo sku = skuService.getSku(skuId);


        // 用户id
        String memberId = String.valueOf(request.getAttribute("memberId"));
        OmsCartItem cartItem = new OmsCartItem(sku,quantity,Long.parseLong(memberId));


        // 未登录存cookie，登录存DB
        if(StringUtils.isBlank(memberId)) {
            String cartCookie = CookieUtil.getCookieValue(request,"cartItemList",true);
            List<OmsCartItem> cartItemList = new LinkedList<>();
            if (StringUtils.isNotBlank(cartCookie)) {
                cartItemList = JSON.parseArray(cartCookie,OmsCartItem.class);
            }

            int exitIndex = cartItemList.indexOf(cartItem);
            if (exitIndex > -1) {
                cartItemList.get(exitIndex).setQuantity(cartItemList.get(exitIndex).getQuantity()+cartItem.getQuantity());
            } else {
                cartItemList.add(cartItem);
            }
            CookieUtil.setCookie(request,response,"cartListCookie",cartItemList,60*60*72,true);
        } else {
            OmsCartItem cartDB = cartService.getCartItem(cartItem);
            if (null == cartDB) {
                cartDB = cartItem;
                cartService.saveCartItem(cartItem);
            } else {
                cartDB.setQuantity(cartItem.getQuantity()+cartDB.getQuantity());
                cartService.updateCartItem(cartDB);
            }
            cartService.flushCartCache(memberId);
        }
        return "redirect:/success.html";
    }

    @LoginRequired(loginSuccess=false)
    @RequestMapping("cartList")
    public String cartList(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        List<OmsCartItem> cartItems = new LinkedList<>();
        String memberId = String.valueOf(request.getAttribute("memberId"));
        if (StringUtils.isBlank(memberId)) {
            String cookieStr = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cookieStr)) {
                cartItems = JSON.parseArray(cookieStr,OmsCartItem.class);
            }
        } else {
            cartItems = cartService.listCart(memberId);
        }
        map.put("cartList",cartItems);
        return "cartList";
    }

}
