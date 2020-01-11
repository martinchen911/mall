package com.cf.mall.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * pms_base_catalog1
 * @author 
 */
@Data
public class PmsBaseCatalog1 implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类名称
     */
    @Column
    private String name;

    /**
     * 二级分类集合
     */
    @Transient
    private List<PmsBaseCatalog2> catalog2s;

    private static final long serialVersionUID = 1L;
}