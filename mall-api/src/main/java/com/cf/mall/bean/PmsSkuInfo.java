package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * pms_sku_info
 * @author 
 */
@Data
public class PmsSkuInfo implements Serializable {
    /**
     * 库存id(itemID)
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 价格
     */
    private Double price;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 商品规格描述
     */
    private String skuDesc;

    private Double weight;

    /**
     * 品牌(冗余)
     */
    private Long tmId;

    /**
     * 三级分类id（冗余)
     */
    private Long catalog3Id;

    /**
     * 默认显示图片(冗余)
     */
    private String skuDefaultImg;

    @Transient
    private List<PmsSkuImage> skuImageList;

    @Transient
    private List<PmsSkuAttrValue> skuAttrValueList;

    @Transient
    private List<PmsSkuSaleAttrValue> skuSaleAttrValueList;

    private static final long serialVersionUID = 1L;
}