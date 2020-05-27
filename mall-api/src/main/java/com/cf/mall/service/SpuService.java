package com.cf.mall.service;

import com.cf.mall.bean.PmsProductImage;
import com.cf.mall.bean.PmsProductInfo;
import com.cf.mall.bean.PmsProductSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品
 *
 * @Author chen
 * @Date 2020/1/12
 */
@FeignClient("manage-service")
public interface SpuService {

    /**
     * 商品列表
     * @param catalog3Id 三级分类
     * @return
     */
    @GetMapping("/spu/spuList")
    List<PmsProductInfo> spuList(String catalog3Id);

    /**
     * 商品销售属性列表
     * @param spuId 商品id
     * @return
     */
    @GetMapping("/spu/spuSaleAttrList")
    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    /**
     * 图片列表
     * @param spuId
     * @return
     */
    @GetMapping("/spu/spuImageList")
    List<PmsProductImage> spuImageList(String spuId);

    /**
     * 保存 spu
     * @param productInfo
     */
    @PostMapping("/spu/saveSpuInfo")
    void saveSpuInfo(PmsProductInfo productInfo);

    /**
     * 查询销售属性
     * @param spuId
     * @return
     */
    @GetMapping("/spu/spuSaleAttrListCheckBySku")
    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(@RequestParam("spuId") Long spuId,@RequestParam("skuId") Long skuId);
}
