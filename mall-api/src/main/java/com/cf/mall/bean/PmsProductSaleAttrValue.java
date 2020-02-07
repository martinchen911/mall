package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * pms_product_sale_attr_value
 * @author 
 */
@Data
public class PmsProductSaleAttrValue implements Serializable {
    /**
     * id
     */
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
     * 销售属性值名称
     */
    private String saleAttrValueName;

    /**
     * 是否选中
     */
    @Transient
    private String isCheck;

    private static final long serialVersionUID = 1L;
}