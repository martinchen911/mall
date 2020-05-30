package com.cf.mall.service;

import com.cf.mall.bean.PmsSkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存存储单元
 * @Author chen
 * @Date 2020/1/14
 */
@FeignClient("manage-service")
public interface SkuService {

    /**
     * 保存sku
     * @param skuInfo
     */
    @PostMapping("/sku/saveSkuInfo")
    void saveSkuInfo(@RequestBody PmsSkuInfo skuInfo);

    /**
     * 获取 sku
     * @param id
     * @return
     */
    @PostMapping("/sku/getSku")
    PmsSkuInfo getSku(@RequestParam String id);

    /**
     * 根据商品id获取sku
     * @param productId
     * @return
     */
    @PostMapping("/sku/listSku")
    List<PmsSkuInfo> listSku(@RequestParam("productId") Long productId);

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/sku/listSku1")
    List<PmsSkuInfo> listSku1();

    @PostMapping("/sku/checkPrice")
    boolean checkPrice(@RequestParam("id") Long id , @RequestParam("price") BigDecimal price);
}
