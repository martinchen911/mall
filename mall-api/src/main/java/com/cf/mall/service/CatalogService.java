package com.cf.mall.service;

import com.cf.mall.bean.PmsBaseCatalog1;
import com.cf.mall.bean.PmsBaseCatalog2;
import com.cf.mall.bean.PmsBaseCatalog3;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 平台类目服务
 *
 * @Author chen
 * @Date 2020/1/10
 */
@FeignClient("manage-service")
public interface CatalogService {

    @GetMapping("/vatalog/getCatalog1")
    List<PmsBaseCatalog1> getCatalog1();
    @GetMapping("/catalog/getCatalog2")
    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);
    @GetMapping("/catalog/getCatalog3")
    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
