package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * pms_base_catalog2
 * @author 
 */
@Data
public class PmsBaseCatalog2 implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 二级分类名称
     */
    @Column
    private String name;

    /**
     * 一级分类编号
     */
    @Column
    private Integer catalog1Id;

    /**
     * 三级分类集合
     */
    @Transient
    private List<PmsBaseCatalog3> catalog3s;

    private static final long serialVersionUID = 1L;
}