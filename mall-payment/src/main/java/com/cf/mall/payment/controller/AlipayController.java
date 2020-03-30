package com.cf.mall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.bean.OmsOrder;
import com.cf.mall.bean.PaymentInfo;
import com.cf.mall.service.OrderService;
import com.cf.mall.service.PaymentService;
import com.cf.mall.util.ActiveMQUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author chen
 * @Date 2020/3/27
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {

    @Autowired
    PaymentService paymentService;
    @Reference
    OrderService orderService;
    @Autowired
    ActiveMQUtil activeMQUtil;


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
        return "finish";
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping("submit")
    public String alipay(String outTradeNo, String totalAmount) {
        String alipay = paymentService.alipay(outTradeNo, totalAmount);

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

        // 延迟检查支付结果
        paymentService.sendDelayPaymentResultCheck(outTradeNo,5);

        return alipay;
    }

}
