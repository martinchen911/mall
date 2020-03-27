package com.cf.mall.service;

import com.cf.mall.bean.OmsOrder;

/**
 * @Author chen
 * @Date 2020/3/25
 */
public interface OrderService {


    String genTradeCode(String memberId);


    boolean checkTradeCode(String memberId,String code);

    void save(OmsOrder order);

    OmsOrder getOrderByOrderNo(String outOrderNo);
}
