package com.cf.mall.service;

import com.cf.mall.bean.PmsSearchParam;
import com.cf.mall.bean.PmsSearchSkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @Author chen
 * @Date 2020/3/8
 */
@FeignClient("search-service")
public interface SearchService {


    /**
     * 搜索
     * @param param
     * @return
     */
    @PostMapping("/list")
    List<PmsSearchSkuInfo> list(@RequestBody PmsSearchParam param);
}
