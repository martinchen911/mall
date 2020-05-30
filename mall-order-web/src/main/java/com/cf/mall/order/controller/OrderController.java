package com.cf.mall.order.controller;

import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.bean.OmsCartItem;
import com.cf.mall.bean.OmsOrder;
import com.cf.mall.bean.OmsOrderItem;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import com.cf.mall.service.CartService;
import com.cf.mall.service.MemberService;
import com.cf.mall.service.OrderService;
import com.cf.mall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author chen
 * @Date 2020/3/21
 */
@Controller
public class OrderController {

    @Autowired
    CartService cartService;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;
    @Autowired
    SkuService skuService;


    /**
     * 提交订单
     * @param addressId
     * @param tradeCode
     * @param map
     * @param request
     * @param response
     * @return
     */
    @PostMapping("submitOrder")
    @LoginRequired
    public ModelAndView submitOrder(String addressId, String tradeCode, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("fail");
        if (StringUtils.isNotBlank(tradeCode)) {
            String memberId = String.valueOf(request.getAttribute("memberId"));
            String nickName = String.valueOf(request.getAttribute("nickName"));
            // 校验tradeCode
            boolean checkTradeCode = orderService.checkTradeCode(memberId, tradeCode);
            if (checkTradeCode) {

                // 1.验库存

                // 2.验价
                List<OmsCartItem> cartItems = cartService.listCart(memberId);
                cartItems = cartItems.stream()
                        .filter(x -> x.getIsChecked().equals("1")).collect(Collectors.toList());
                for (OmsCartItem x : cartItems) {
                    if (skuService.checkPrice(x.getProductSkuId(),x.getPrice())) {
                        return mv;
                    }
                }

                // 3.封装订单数据
                OmsOrder order = buildOrder(memberId,nickName,addressId);
                orderService.save(order);

                // 4.删除对应购物车数据
                //cartService.removeItem(new OmsCartItem(memberId,"1"));



                // 重定向到支付页面
                mv.setViewName("redirect:http://payment.mall.com:8087/index");
                mv.addObject("outTradeNo",order.getOrderSn());
                mv.addObject("totalAmount",order.getTotalAmount());


                return mv;
            }
        }
        return mv;
    }

    /**
     * 封装订单信息
     * @param memberId
     * @param nickName
     * @param addressId
     * @return
     */
    private OmsOrder buildOrder(String memberId,String nickName, String addressId) {
        OmsOrder order = new OmsOrder();

        // 获得选中商品列表
        List<OmsCartItem> cartItems = cartService.listCart(memberId);
        cartItems = cartItems.stream()
                .filter(x -> x.getIsChecked().equals("1")).collect(Collectors.toList());

        // 转化为订单详情对象
        List<OmsOrderItem> orderItemList = cartItems.stream().map(OmsOrderItem::new).collect(Collectors.toList());

        // 获取地址
        UmsMemberReceiveAddress address =  memberService.getAddress(addressId);

        // 计算总金额
        BigDecimal totalAmount = orderItemList.stream()
                .map(x -> x.getProductPrice().multiply(new BigDecimal(x.getProductQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        // 订单编号
        String orderSn = "mall"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0,8);


        // 封装信息
        order.setMemberId(Long.parseLong(memberId));
        order.setOrderSn(orderSn);
        order.setCreateTime(new Date());
        order.setMemberUsername(nickName);
        order.setTotalAmount(totalAmount);
        order.setStatus(0);
        order.setOrderType(0);
        order.setBillReceiverPhone(address.getPhoneNumber());
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        order.setReceiverRegion(address.getRegion());
        order.setConfirmStatus(0);
        order.setOrderItemList(orderItemList);

        return order;
    }

    /**
     * 结算
     * @param map
     * @param request
     * @return
     */
    @GetMapping("trade")
    @LoginRequired
    public String trade(ModelMap map, HttpServletRequest request) {
        String memberId = String.valueOf(request.getAttribute("memberId"));

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

        // 结算码
        String tradeCode = orderService.genTradeCode(memberId);

        map.put("orderDetailList",orderItemList);
        map.put("userAddressList",addresses);
        map.put("totalAmount",totalAmount);
        map.put("tradeCode",tradeCode);

        return "trade";
    }
}
