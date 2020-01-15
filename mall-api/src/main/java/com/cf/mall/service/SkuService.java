package com.cf.mall.service;

import com.cf.mall.bean.PmsSkuInfo;

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
}
