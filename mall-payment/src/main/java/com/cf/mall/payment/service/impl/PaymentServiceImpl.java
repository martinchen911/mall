package com.cf.mall.payment.service.impl;

import com.cf.mall.bean.PaymentInfo;
import com.cf.mall.payment.mapper.PaymentInfoMapper;
import com.cf.mall.service.PaymentService;
import com.cf.mall.util.ActiveMQUtil;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;

/**
 * @Author chen
 * @Date 2020/3/27
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentInfoMapper paymentInfoMapper;
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
}
