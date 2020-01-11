package com.cf.mall.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * pms_base_attr_value
 * @author 
 */
@Data
public class PmsBaseAttrValue implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 属性值名称
     */
    private String valueName;

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 启用：1 停用：0 1
     */
    private String isEnabled;

    private static final long serialVersionUID = 1L;
}