package com.cf.mall.service;

import com.cf.mall.bean.PmsSearchParam;
import com.cf.mall.bean.PmsSearchSkuInfo;

import java.util.List;


/**
 * @Author chen
 * @Date 2020/3/8
 */
public interface SearchService {


    /**
     * 搜索
     * @param param
     * @return
     */
    List<PmsSearchSkuInfo> list(PmsSearchParam param);
}
