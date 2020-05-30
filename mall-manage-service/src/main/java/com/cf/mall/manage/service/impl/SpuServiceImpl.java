package com.cf.mall.manage.service.impl;

import com.cf.mall.bean.PmsProductImage;
import com.cf.mall.bean.PmsProductInfo;
import com.cf.mall.bean.PmsProductSaleAttr;
import com.cf.mall.bean.PmsProductSaleAttrValue;
import com.cf.mall.manage.mapper.PmsProductImageMapper;
import com.cf.mall.manage.mapper.PmsProductInfoMapper;
import com.cf.mall.manage.mapper.PmsProductSaleAttrMapper;
import com.cf.mall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.cf.mall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/12
 */
@RequestMapping("spu")
@RestController
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper productInfoMapper;
    @Autowired
    private PmsProductImageMapper productImageMapper;
    @Autowired
    private PmsProductSaleAttrMapper productSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper productSaleAttrValueMapper;

    @PostMapping("spuList")
    @Override
    public List<PmsProductInfo> spuList(@RequestParam String catalog3Id) {
        Example e = new Example(PmsProductInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        return productInfoMapper.selectByExample(e);
    }

    @PostMapping("spuSaleAttrList")
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(@RequestParam String spuId) {
        PmsProductSaleAttr saleAttr = new PmsProductSaleAttr();
        saleAttr.setProductId(Long.parseLong(spuId));
        List<PmsProductSaleAttr> saleAttrs = productSaleAttrMapper.select(saleAttr);
        saleAttrs.forEach(x -> {
            PmsProductSaleAttrValue value = new PmsProductSaleAttrValue();
            value.setSaleAttrId(x.getId());
            x.setSpuSaleAttrValueList(productSaleAttrValueMapper.select(value));
        });
        return saleAttrs;
    }
    @PostMapping("spuImageList")
    @Override
    public List<PmsProductImage> spuImageList(@RequestParam String spuId) {
        PmsProductImage image = new PmsProductImage();
        image.setProductId(Long.parseLong(spuId));
        return productImageMapper.select(image);
    }

    @PostMapping("saveSpuInfo")
    @Override
    public void saveSpuInfo(@RequestBody PmsProductInfo productInfo) {
        productInfoMapper.insertSelective(productInfo);
        productInfo.getSpuImageList().forEach(img -> {
            img.setProductId(productInfo.getId());
            productImageMapper.insertSelective(img);
        });
        productInfo.getSpuSaleAttrList().forEach(attr -> {
            attr.setProductId(productInfo.getId());
            productSaleAttrMapper.insertSelective(attr);

            attr.getSpuSaleAttrValueList().forEach(v -> {
                v.setProductId(productInfo.getId());
                v.setSaleAttrId(attr.getId());
                productSaleAttrValueMapper.insertSelective(v);
            });
        });
    }

    @PostMapping("spuSaleAttrListCheckBySku")
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(@RequestParam Long spuId,@RequestParam Long skuId) {
        List<PmsProductSaleAttr> saleAttrs = productSaleAttrMapper.selectSpuSaleAttrListCheckBySku(spuId,skuId);
        return saleAttrs;
    }
}
