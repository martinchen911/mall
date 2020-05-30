package com.cf.mall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.OmsOrder;
import com.cf.mall.bean.OmsOrderItem;
import com.cf.mall.order.mapper.OmsOrderItemMapper;
import com.cf.mall.order.mapper.OmsOrderMapper;
import com.cf.mall.service.OrderService;
import com.cf.mall.util.ActiveMQUtil;
import com.cf.mall.util.RedisUtil;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * @Author chen
 * @Date 2020/3/25
 */
@RestController
public class OrderServiceImpl implements OrderService {

    @Autowired
    OmsOrderMapper orderMapper;
    @Autowired
    OmsOrderItemMapper orderItemMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ActiveMQUtil activeMQUtil;

    @PostMapping("updateByOrderSn")
    @Override
    public void updateByOrderSn(@RequestBody OmsOrder order) {
        Example e = new Example(OmsOrder.class);
        e.createCriteria().andEqualTo("orderSn",order.getOrderSn());
        order.setStatus(1);
        orderMapper.updateByExampleSelective(order,e);

        // 发送一个订单已支付的队列，提供给库存消费
        TextMessage message = new ActiveMQTextMessage();

        // 获取订单及详情数据
        OmsOrder orderMsg = orderMapper.selectOne(order);
        Example e2 = new Example(OmsOrderItem.class);
        e2.createCriteria().andEqualTo("orderSn",order.getOrderSn());
        orderMsg.setOrderItemList(orderItemMapper.selectByExample(e2));
        try {
            message.setText(JSON.toJSONString(orderMsg));
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        activeMQUtil.send("ORDER_PAY_QUEUE",message);
    }

    @PostMapping("genTradeCode")
    @Override
    public String genTradeCode(@RequestParam String memberId) {
        String v = UUID.randomUUID().toString();
        String key = "user:"+ memberId + v +":tradeCode";
        try (Jedis jedis = redisUtil.getJedis()){
            jedis.setex(key,60*30, "0");
        } catch (Exception e) {
            e.printStackTrace();
            v = null;
        }
        return  v;
    }

    @PostMapping("checkTradeCode")
    @Override
    public boolean checkTradeCode(@RequestParam String memberId,@RequestParam String code) {
        String key = "user:"+ memberId + code +":tradeCode";
        try (Jedis jedis = redisUtil.getJedis()){
            String lua = "if redis.call('get',KEYS[1])" +
                    "then return redis.call('del',KEYS[1])" +
                    "else return 0 end";
            Long eval = (Long)jedis.eval(lua, 1,new String[]{key});
            return eval == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Transactional()
    @PostMapping("save")
    @Override
    public void save(@RequestBody OmsOrder order) {
        orderMapper.insertSelective(order);
        order.getOrderItemList().stream().forEach(x -> {
            x.setOrderId(order.getId());
            x.setOrderSn(order.getOrderSn());
            orderItemMapper.insertSelective(x);
        });
    }

    @PostMapping("getOrderByOrderNo")
    @Override
    public OmsOrder getOrderByOrderNo(@RequestParam String outOrderNo) {
        Example e = new Example(OmsOrder.class);
        e.createCriteria().andEqualTo("orderSn",outOrderNo);
        return orderMapper.selectOneByExample(e);
    }
}
