package com.cf.mall.service;

import com.cf.mall.bean.PmsSkuInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存存储单元
 * @Author chen
 * @Date 2020/1/14
 */
public interface SkuService {

    /**
     * 保存sku
     * @param skuInfo
     */
    void saveSkuInfo(PmsSkuInfo skuInfo);

    /**
     * 获取 sku
     * @param id
     * @return
     */
    PmsSkuInfo getSku(String id);

    /**
     * 根据商品id获取sku
     * @param productId
     * @return
     */
    List<PmsSkuInfo> listSku(Long productId);

    /**
     * 查询所有
     * @return
     */
    List<PmsSkuInfo> listSku();

    boolean checkPrice(Long id ,BigDecimal price);
}
