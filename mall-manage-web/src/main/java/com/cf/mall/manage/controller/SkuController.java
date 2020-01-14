package com.cf.mall.manage.controller;

import com.cf.mall.bean.PmsSkuInfo;
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


    @PostMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody PmsSkuInfo skuInfo) {

    }

}
