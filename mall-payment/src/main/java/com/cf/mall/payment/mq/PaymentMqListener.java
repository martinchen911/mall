package com.cf.mall.payment.mq;

import com.cf.mall.bean.PaymentInfo;
import com.cf.mall.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author chen
 * @Date 2020/3/30
 */
@Component
public class PaymentMqListener {

    @Autowired
    PaymentService paymentService;

    @JmsListener(destination = "PAYMENT_CHECK_QUEUE",containerFactory = "jmsQueueListener")
    public void consumePaymentCheckResult(MapMessage map) throws JMSException {
        String orderSn = map.getString("orderSn");
        int times = map.getInt("times");

        Map<String, String> alipay = paymentService.checkAlipay(orderSn);

        if (alipay != null && !alipay.isEmpty()) {
            if (alipay.get("trade_status").equals("TRADE_SUCCESS")) {
                PaymentInfo info = new PaymentInfo();
                info.setOrderSn(alipay.get("out_trade_no"));
                info.setTotalAmount(new BigDecimal(alipay.get("total_amount")));
                info.setSubject(alipay.get("subject"));
                info.setAlipayTradeNo(alipay.get("trade_status"));
                info.setCallbackContent(alipay.get("call_back_content"));
                info.setCallbackTime(new Date());
                info.setPaymentStatus("已支付");
                paymentService.update(info);
                return;
            }
        }
        if (times > 0) {
            times--;
            paymentService.sendDelayPaymentResultCheck(orderSn,times);
        }
    }

}
