package com.cf.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.bean.PmsProductImage;
import com.cf.mall.bean.PmsProductInfo;
import com.cf.mall.bean.PmsProductSaleAttr;
import com.cf.mall.util.UploadUtil;
import com.cf.mall.service.SpuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/12
 */
@RestController
@CrossOrigin
public class SpuController {

    @Reference
    private SpuService spuService;

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        return UploadUtil.uploadImage(multipartFile);
    }

    @PostMapping("/saveSpuInfo")
    public void saveSpuInfo(@RequestBody PmsProductInfo productInfo) {
        spuService.saveSpuInfo(productInfo);
    }


    @GetMapping("/spuList")
    public List<PmsProductInfo> spuList(String catalog3Id) {
        return spuService.spuList(catalog3Id);
    }
    @GetMapping("/spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        return spuService.spuSaleAttrList(spuId);
    }
    @GetMapping("/spuImageList")
    public List<PmsProductImage> spuImageList(String spuId) {
        return spuService.spuImageList(spuId);
    }
}
