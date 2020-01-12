package com.cf.mall.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * pms_base_sale_attr
 * @author 
 */
@Data
public class PmsBaseSaleAttr implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 销售属性名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}