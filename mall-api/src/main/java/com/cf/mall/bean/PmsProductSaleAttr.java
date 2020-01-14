package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * pms_product_sale_attr
 * @author 
 */
@Data
public class PmsProductSaleAttr implements Serializable {
    /**
     * id
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
     * 销售属性id
     */
    private Long saleAttrId;

    /**
     * 销售属性名称(冗余)
     */
    private String saleAttrName;

    @Transient
    private List<PmsProductSaleAttrValue> spuSaleAttrValueList;

    private static final long serialVersionUID = 1L;
}