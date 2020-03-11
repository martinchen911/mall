package com.cf.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索面包屑
 * @Author chen
 * @Date 2020/3/11
 */
@Data
@AllArgsConstructor
public class PmsSearchCrumb implements Serializable {

    /**
     * 平台属性值id
     */
    private String valueId;
    /**
     * 平台属性值名称
     */
    private String valueName;
    /**
     * url参数
     */
    private String urlParam;

}
