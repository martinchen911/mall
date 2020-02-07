package com.cf.mall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.PmsProductSaleAttr;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.service.SkuService;
import com.cf.mall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author chen
 * @Date 2020/1/15
 */
@Controller
public class SpuController {

    @Reference
    private SkuService skuService;
    @Reference
    private SpuService spuService;

    @GetMapping("{id}.html")
    public String index(@PathVariable("id") String id, ModelMap map) {
        PmsSkuInfo sku = skuService.getSku(id);
        map.put("skuInfo",sku);
        List<PmsProductSaleAttr> saleAttrs = spuService.spuSaleAttrListCheckBySku(sku.getProductId(), sku.getId());
        map.put("spuSaleAttrListCheckBySku",saleAttrs);

        List<PmsSkuInfo> pmsSkuInfos = skuService.listSku(sku.getProductId());

        Map<String,Long> valuesSku = new HashMap<>();

        pmsSkuInfos.stream().forEach(s -> {

            String key = s.getSkuSaleAttrValueList().stream().map(v -> v.getSaleAttrValueId().toString())
                    .collect(Collectors.joining("|"));
            valuesSku.put(key,s.getId());
        });

        map.put("valuesSku", JSON.toJSON(valuesSku));
        return "item";
    }
}
