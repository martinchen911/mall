package com.cf.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * pms_product_info
 * @author 
 */
@Data
public class PmsProductInfo implements Serializable {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述(后台简述）
     */
    private String description;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 品牌id
     */
    private Long tmId;

    private List<PmsProductImage> spuImageList;

    private List<PmsProductSaleAttr> spuSaleAttrList;



    private static final long serialVersionUID = 1L;
}