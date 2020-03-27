package com.cf.mall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.bean.OmsOrder;
import com.cf.mall.bean.PaymentInfo;
import com.cf.mall.payment.properties.AlipayProperties;
import com.cf.mall.service.OrderService;
import com.cf.mall.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chen
 * @Date 2020/3/27
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    AlipayProperties alipayProperties;
    @Autowired
    PaymentService paymentService;
    @Reference
    OrderService orderService;


    @LoginRequired
    @ResponseBody
    @RequestMapping("callback/return")
    public String callback(ModelMap map,HttpServletRequest request) {
        String sign = request.getParameter("sign");
        String tradeNo = request.getParameter("trade_no");
        String outTradeNo = request.getParameter("out_trade_no");
        String totalAmount = request.getParameter("total_amount");
        String subject = request.getParameter("subject");
        String callBackContent = request.getQueryString();
        // 验签
        if (StringUtils.isNotBlank(sign)) {
            // 修改支付状态
            PaymentInfo info = new PaymentInfo();
            info.setOrderSn(outTradeNo);
            info.setPaymentStatus("已支付");
            info.setTotalAmount(new BigDecimal(totalAmount));
            info.setSubject(subject);
            info.setAlipayTradeNo(tradeNo);
            info.setCallbackContent(callBackContent);
            info.setCallbackTime(new Date());
            paymentService.update(info);
        }
        // 支付成功，启动对应服务：订单 -> 库存 -> 物流

        return "finish";
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping("submit")
    public String alipay(String outTradeNo, String totalAmount, ModelMap map, HttpServletRequest request) {
        try {
            AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
            payRequest.setReturnUrl(alipayProperties.returnPaymentUrl);
            payRequest.setNotifyUrl(alipayProperties.notifyPaymentUrl);

            Map<String,Object> param = new HashMap<>(4);
            param.put("out_trade_no",outTradeNo);
            param.put("product_code","FAST_INSTANT_TRADE_PAY");
            param.put("total_amount","0.01");
            param.put("subject","支付测试"+outTradeNo);
            payRequest.setBizContent(JSON.toJSONString(param));

            String body = alipayClient.pageExecute(payRequest).getBody();

            // 保存用户支付信息
            OmsOrder order = orderService.getOrderByOrderNo(outTradeNo);
            PaymentInfo info = new PaymentInfo();
            info.setCreateTime(new Date());
            info.setOrderId(String.valueOf(order.getId()));
            info.setOrderSn(order.getOrderSn());
            info.setPaymentStatus("未付款");
            info.setSubject("支付测试"+outTradeNo);
            info.setTotalAmount(new BigDecimal(totalAmount));
            info.setTotalAmount(order.getTotalAmount());
            paymentService.save(info);

            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
