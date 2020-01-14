package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.*;
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
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品名称
     */
    @Column
    private String productName;

    /**
     * 商品描述(后台简述）
     */
    @Column
    private String description;

    /**
     * 三级分类id
     */
    @Column
    private Long catalog3Id;

    /**
     * 品牌id
     */
    @Column
    private Long tmId;

    @Transient
    private List<PmsProductImage> spuImageList;

    @Transient
    private List<PmsProductSaleAttr> spuSaleAttrList;



    private static final long serialVersionUID = 1L;
}