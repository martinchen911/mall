package com.cf.mall.service;

import com.cf.mall.bean.OmsCartItem;

/**
 * @Author chen
 * @Date 2020/3/13
 */
public interface CartService {


    OmsCartItem getCartItem(OmsCartItem cartItem);

    void saveCartItem(OmsCartItem cartItem);

    void updateCartItem(OmsCartItem cartDB);

    void flushCartCache(Long memberId);
}
