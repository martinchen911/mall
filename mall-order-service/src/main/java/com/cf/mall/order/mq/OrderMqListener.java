package com.cf.mall.order.mq;

import com.alibaba.druid.sql.dialect.odps.ast.OdpsAddStatisticStatement;
import com.cf.mall.bean.OmsOrder;
import com.cf.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * 订单消息监听
 * @Author chen
 * @Date 2020/3/29
 */
@Component
public class OrderMqListener {

    @Autowired
    OrderService orderService;

    @JmsListener(destination = "PAYHMENT_SUCCESS_QUEUE",containerFactory = "jmsQueueListener")
    public void consumePaymentResult(MapMessage map) throws JMSException {
        OmsOrder order = new OmsOrder();
        order.setOrderSn(map.getString("orderSn"));
        orderService.updateByOrderSn(order);
    }


}
