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
import java.util.Map;
import java.util.stream.Collectors;

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
        cartItem.setQuantity(null);
        Jedis jedis = redisUtil.getJedis();
        String s = jedis.hget("user:"+cartItem.getMemberId()+":cart",cartItem.getProductSkuId().toString());
        return StringUtils.isBlank(s)?
                cartItemMapper.selectOne(cartItem)
                :JSON.parseObject(s,OmsCartItem.class);
    }

    @Override
    public void saveCartItem(OmsCartItem cartItem) {
        cartItemMapper.insertSelective(cartItem);
    }

    @Override
    public void updateCartItem(OmsCartItem cartItem) {
        cartItemMapper.updateByPrimaryKeySelective(cartItem);
    }

    @Override
    public void flushCartCache(String memberId) {
        String key = "user:"+memberId+":cart";
        try (Jedis jedis = redisUtil.getJedis()) {
            Example e = new Example(OmsCartItem.class);
            e.createCriteria().andEqualTo("memberId",memberId);
            List<OmsCartItem> cartItems = cartItemMapper.selectByExample(e);

            jedis.del(key);
            Map<String,String> map = cartItems.stream()
                    .collect(Collectors.toMap(x -> String.valueOf(x.getProductSkuId()),JSON::toJSONString));
            jedis.hmset(key,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OmsCartItem> listCart(String memberId) {
        List<OmsCartItem> list = null;
        try (Jedis jedis = redisUtil.getJedis()) {
            List<String> hvals = jedis.hvals("user:" + memberId + ":cart");
            list = hvals.stream().map(x -> JSON.parseObject(x,OmsCartItem.class)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void checkedCart(OmsCartItem cartItem) {

        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("memberId",cartItem.getMemberId())
                .andEqualTo("productSkuId",cartItem.getProductSkuId());
        cartItemMapper.updateByExampleSelective(cartItem,e);
        flushCartCache(cartItem.getMemberId().toString());
    }
}
