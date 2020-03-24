package com.cf.mall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.bean.OmsCartItem;
import com.cf.mall.bean.OmsOrderItem;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import com.cf.mall.service.CartService;
import com.cf.mall.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author chen
 * @Date 2020/3/21
 */
@Controller
public class OrderController {

    @Reference
    CartService cartService;
    @Reference
    MemberService memberService;

    @PostMapping("submitOrder")
    @LoginRequired
    public String submitOrder(String addressId,ModelMap map, HttpServletRequest request, HttpServletResponse response) {




        return "";
    }

    @GetMapping("trade")
    @LoginRequired
    public String trade(ModelMap map, HttpServletRequest request) {
        String memberId = String.valueOf(request.getAttribute("memberId"));
        String nikeName = String.valueOf(request.getAttribute("nikeName"));

        // 获得选中商品列表
        List<OmsCartItem> cartItems = cartService.listCart(memberId);
        cartItems = cartItems.stream()
                .filter(x -> x.getIsChecked().equals("1")).collect(Collectors.toList());

        // 转化为订单对象
        List<OmsOrderItem> orderItemList = cartItems.stream().map(OmsOrderItem::new).collect(Collectors.toList());

        // 获取收获地址列表
        List<UmsMemberReceiveAddress> addresses = memberService.selectByMemberKey(memberId);

        // 总价
        BigDecimal totalAmount = orderItemList.stream()
                .map(x -> x.getProductPrice().multiply(new BigDecimal(x.getProductQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        map.put("orderDetailList",orderItemList);
        map.put("userAddressList",addresses);
        map.put("totalAmount",totalAmount);
        map.put("tradeCode","");

        return "trade";
    }
}
