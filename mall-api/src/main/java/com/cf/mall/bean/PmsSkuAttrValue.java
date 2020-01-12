package com.cf.mall.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * pms_sku_attr_value
 * @author 
 */
@Data
public class PmsSkuAttrValue implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 属性id（冗余)
     */
    private Long attrId;

    /**
     * 属性值id
     */
    private Long valueId;

    /**
     * skuid
     */
    private Long skuId;

    private static final long serialVersionUID = 1L;
}