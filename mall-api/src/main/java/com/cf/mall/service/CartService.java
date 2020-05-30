package com.cf.mall.service;

import com.cf.mall.bean.OmsCartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/3/13
 */
@FeignClient("cart-service")
public interface CartService {


    @PostMapping("getCartItem")
    OmsCartItem getCartItem(@RequestBody OmsCartItem cartItem);

    @PostMapping("saveCartItem")
    void saveCartItem(@RequestBody OmsCartItem cartItem);

    @PostMapping("updateCartItem")
    void updateCartItem(@RequestBody OmsCartItem cartItem);

    @PostMapping("flushCartCache")
    void flushCartCache(@RequestParam String memberId);

    @PostMapping("listCart")
    List<OmsCartItem> listCart(@RequestParam String memberId);

    @PostMapping("checkedCart")
    void checkedCart(@RequestBody OmsCartItem cartItem);

    @PostMapping("removeItem")
    void removeItem(@RequestBody OmsCartItem cartItem);
}
