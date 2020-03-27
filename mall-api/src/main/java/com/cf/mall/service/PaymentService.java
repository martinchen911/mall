package com.cf.mall.service;

import com.cf.mall.bean.PaymentInfo;

/**
 * @Author chen
 * @Date 2020/3/27
 */
public interface PaymentService {

    /**
     * 保存
     * @param paymentInfo
     */
    void save(PaymentInfo paymentInfo);

    void update(PaymentInfo paymentInfo);
}
