package com.cf.mall.service;

import com.cf.mall.bean.OmsOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author chen
 * @Date 2020/3/25
 */
@FeignClient("order-service")
public interface OrderService {


    @PostMapping("updateByOrderSn")
    void updateByOrderSn(@RequestBody OmsOrder order);

    @PostMapping("genTradeCode")
    String genTradeCode(@RequestParam String memberId);

    @PostMapping("checkTradeCode")
    boolean checkTradeCode(@RequestParam String memberId,@RequestParam String code);

    @PostMapping("save")
    void save(@RequestBody OmsOrder order);

    @PostMapping("getOrderByOrderNo")
    OmsOrder getOrderByOrderNo(@RequestParam String outOrderNo);
}
