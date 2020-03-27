package com.cf.mall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.OmsOrder;
import com.cf.mall.order.mapper.OmsOrderItemMapper;
import com.cf.mall.order.mapper.OmsOrderMapper;
import com.cf.mall.service.OrderService;
import com.cf.mall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.UUID;

/**
 * @Author chen
 * @Date 2020/3/25
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OmsOrderMapper orderMapper;
    @Autowired
    OmsOrderItemMapper orderItemMapper;


    @Autowired
    RedisUtil redisUtil;

    @Override
    public String genTradeCode(String memberId) {
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

    @Override
    public boolean checkTradeCode(String memberId,String code) {
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
    @Override
    public void save(OmsOrder order) {
        orderMapper.insertSelective(order);
        order.getOrderItemList().stream().forEach(x -> {
            x.setOrderId(order.getId());
            x.setOrderSn(order.getOrderSn());
            orderItemMapper.insertSelective(x);
        });
    }

    @Override
    public OmsOrder getOrderByOrderNo(String outOrderNo) {
        Example e = new Example(OmsOrder.class);
        e.createCriteria().andEqualTo("orderSn",outOrderNo);
        return orderMapper.selectOneByExample(e);
    }
}
