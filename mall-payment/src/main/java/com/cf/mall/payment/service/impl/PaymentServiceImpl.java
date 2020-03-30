package com.cf.mall.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.cf.mall.bean.PaymentInfo;
import com.cf.mall.payment.mapper.PaymentInfoMapper;
import com.cf.mall.payment.properties.AlipayProperties;
import com.cf.mall.service.PaymentService;
import com.cf.mall.util.ActiveMQUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chen
 * @Date 2020/3/27
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentInfoMapper paymentInfoMapper;
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    AlipayProperties alipayProperties;
    @Autowired
    ActiveMQUtil activeMQUtil;

    @Override
    public void save(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }

    @Override
    public void update(PaymentInfo paymentInfo) {
        Example e = new Example(PaymentInfo.class);
        e.createCriteria().andEqualTo("orderSn",paymentInfo.getOrderSn());

        PaymentInfo info = paymentInfoMapper.selectOneByExample(e);
        // 幂等性校验
        if (StringUtils.isNotBlank(info.getPaymentStatus()) && info.getPaymentStatus().equals("已支付")) {
            System.out.println("进入幂等性校验，退出函数！！！");
            return;
        }
        paymentInfoMapper.updateByExampleSelective(paymentInfo,e);

        // 支付成功，启动对应服务：订单 -> 库存 -> 物流
        MapMessage mapMessage = new ActiveMQMapMessage();
        try {
            mapMessage.setString("orderSn",paymentInfo.getOrderSn());
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        activeMQUtil.send("PAYHMENT_SUCCESS_QUEUE",mapMessage);
    }

    @Override
    public String alipay(String outTradeNo, String totalAmount) {

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
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String,String> checkAlipay(String outTradeNo) {
        Map<String,String> result = new HashMap<>(6);

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        Map<String,Object> param = new HashMap<>(2);
        param.put("out_trade_no",outTradeNo);
        request.setBizContent(JSON.toJSONString(param));
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                result.put("out_trade_no",response.getOutTradeNo());
                result.put("trade_no",response.getTradeNo());
                result.put("trade_status",response.getTradeStatus());
                result.put("call_back_content",response.getMsg());
                result.put("subject",response.getSubject());
                result.put("total_amount",response.getTotalAmount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void sendDelayPaymentResultCheck(String outTradeNo, Integer i) {
        MapMessage message = new ActiveMQMapMessage();
        try {
            message.setString("orderSn",outTradeNo);
            message.setInt("times",i);
            // 设置消息延时
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,1000*15*(6-i));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activeMQUtil.send("PAYMENT_CHECK_QUEUE",message);
    }
}
