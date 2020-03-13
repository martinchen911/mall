package com.cf.mall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.OmsCartItem;
import com.cf.mall.cart.mapper.OmsCartItemMapper;
import com.cf.mall.service.CartService;
import com.cf.mall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/3/13
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OmsCartItemMapper cartItemMapper;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public OmsCartItem getCartItem(OmsCartItem cartItem) {
        Jedis jedis = redisUtil.getJedis();
        String s = jedis.get("cartItem_" + cartItem.getMemberId());
        return StringUtils.isBlank(s)?
                cartItemMapper.selectOne(cartItem)
                :JSON.parseObject(s,OmsCartItem.class);
    }

    @Override
    public void saveCartItem(OmsCartItem cartItem) {
        cartItemMapper.insertSelective(cartItem);
        flushCartCache(cartItem.getMemberId());
    }

    @Override
    public void updateCartItem(OmsCartItem cartDB) {
        cartItemMapper.updateByPrimaryKeySelective(cartDB);
    }

    @Override
    public void flushCartCache(Long memberId) {
        Jedis jedis = redisUtil.getJedis();
        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("memberId",memberId);
        List<OmsCartItem> cartItems = cartItemMapper.selectByExample(e);
        jedis.set("cartItem_"+memberId, JSON.toJSONString(cartItems));
    }
}
