package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * pms_base_attr_info
 * @author 
 */
@Data
public class PmsBaseAttrInfo implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 属性名称
     */
    @Column
    private String attrName;

    @Column
    private Long catalog3Id;

    /**
     * 启用：1 停用：0
     */
    @Column
    private String isEnabled;

    @Transient
    private List<PmsBaseAttrValue> attrValueList;

    private static final long serialVersionUID = 1L;
}