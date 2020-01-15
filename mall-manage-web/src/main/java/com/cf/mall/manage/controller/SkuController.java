package com.cf.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.service.SkuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存单元
 * @Author chen
 * @Date 2020/1/14
 */
@RestController
@CrossOrigin
public class SkuController {

    @Reference
    private SkuService skuService;

    @PostMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody PmsSkuInfo skuInfo) {
        skuService.saveSkuInfo(skuInfo);
    }

}
