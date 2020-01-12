package com.cf.mall.service;

import com.cf.mall.bean.PmsBaseCatalog1;
import com.cf.mall.bean.PmsBaseCatalog2;
import com.cf.mall.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * 平台类目服务
 *
 * @Author chen
 * @Date 2020/1/10
 */
public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
