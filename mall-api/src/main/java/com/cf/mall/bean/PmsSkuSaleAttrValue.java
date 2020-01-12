package com.cf.mall.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * pms_sku_sale_attr_value
 * @author 
 */
@Data
public class PmsSkuSaleAttrValue implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 库存单元id
     */
    private Long skuId;

    /**
     * 销售属性id（冗余)
     */
    private Long saleAttrId;

    /**
     * 销售属性值id
     */
    private Long saleAttrValueId;

    /**
     * 销售属性名称(冗余)
     */
    private String saleAttrName;

    /**
     * 销售属性值名称(冗余)
     */
    private String saleAttrValueName;

    private static final long serialVersionUID = 1L;
}