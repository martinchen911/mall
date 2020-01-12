package com.cf.mall.service;

import com.cf.mall.bean.PmsProductImage;
import com.cf.mall.bean.PmsProductInfo;
import com.cf.mall.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * 商品
 *
 * @Author chen
 * @Date 2020/1/12
 */
public interface SpuService {

    /**
     * 商品列表
     * @param catalog3Id 三级分类
     * @return
     */
    List<PmsProductInfo> spuList(String catalog3Id);

    /**
     * 商品销售属性列表
     * @param spuId 商品id
     * @return
     */
    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    /**
     * 图片列表
     * @param spuId
     * @return
     */
    List<PmsProductImage> spuImageList(String spuId);
}
