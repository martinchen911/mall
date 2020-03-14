package com.cf.mall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.OmsCartItem;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.service.CartService;
import com.cf.mall.service.SkuService;
import com.cf.mall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("checkCart")
    public String checkCart(OmsCartItem cartItem,ModelMap map) {

        String memberId = "123";
        cartItem.setMemberId(Long.parseLong(memberId));
        cartService.checkedCart(cartItem);

        List<OmsCartItem> cartItems = cartService.listCart(memberId);
        map.put("cartList",cartItems);

        return "cartListInner";
    }

    @RequestMapping("addToCart")
    public String addToCart(String skuId,Integer quantity,HttpServletRequest request, HttpServletResponse response) {
        PmsSkuInfo sku = skuService.getSku(skuId);


        // 用户id
        Long memberId = 123L;
        OmsCartItem cartItem = new OmsCartItem(sku,quantity,memberId);



        // 未登录存cookie，登录存DB
        if(StringUtils.isBlank(memberId.toString())) {
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
            cartService.flushCartCache(memberId.toString());
        }
        return "redirect:/success.html";
    }

    @RequestMapping("cartList")
    public String cartList(ModelMap map,HttpServletRequest request, HttpServletResponse response) {
        List<OmsCartItem> cartItems = new LinkedList<>();
        String memberId = "123";
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
