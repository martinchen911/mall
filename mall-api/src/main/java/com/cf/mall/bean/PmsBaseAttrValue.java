package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * pms_base_attr_value
 * @author 
 */
@Data
public class PmsBaseAttrValue implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 属性值名称
     */
    @Column
    private String valueName;

    /**
     * 属性id
     */
    @Column
    private Long attrId;

    /**
     * 启用：1 停用：0 1
     */
    @Column
    private String isEnabled;

    private static final long serialVersionUID = 1L;
}