package com.cf.mall.service;

import com.cf.mall.bean.OmsCartItem;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/3/13
 */
public interface CartService {


    OmsCartItem getCartItem(OmsCartItem cartItem);

    void saveCartItem(OmsCartItem cartItem);

    void updateCartItem(OmsCartItem cartItem);

    void flushCartCache(String memberId);

    List<OmsCartItem> listCart(String memberId);

    void checkedCart(OmsCartItem cartItem);
}
