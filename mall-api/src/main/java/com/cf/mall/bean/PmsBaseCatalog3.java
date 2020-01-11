package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * pms_base_catalog3
 * @author 
 */
@Data
public class PmsBaseCatalog3 implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 三级分类名称
     */
    @Column
    private String name;

    /**
     * 二级分类编号
     */
    @Column
    private Long catalog2Id;

    private static final long serialVersionUID = 1L;
}