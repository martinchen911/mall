package com.cf.mall.service;

import com.cf.mall.bean.PaymentInfo;

import java.util.Map;

/**
 * @Author chen
 * @Date 2020/3/27
 */
public interface PaymentService {

    /**
     * 保存
     * @param paymentInfo
     */
    void save(PaymentInfo paymentInfo);

    void update(PaymentInfo paymentInfo);

    /**
     * 生成阿里支付表单
     * @param outTradeNo
     * @param totalAmount
     * @return
     */
    String alipay(String outTradeNo,String totalAmount);

    /**
     * 验证支付结果
     * @param outTradeNo
     * @return
     */
    Map<String,String> checkAlipay(String outTradeNo);

    /**
     * 延时队列发送验证消息
     * @param outTradeNo
     * @param i
     */
    void sendDelayPaymentResultCheck(String outTradeNo, Integer i);
}
