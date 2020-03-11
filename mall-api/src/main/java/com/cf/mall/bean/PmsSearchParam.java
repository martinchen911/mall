package com.cf.mall.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author chen
 * @Date 2020/3/8
 */
@Data
public class PmsSearchParam implements Serializable {

    private String keyword;
    private String catalog3Id;
    private List<String> valueId;

}
