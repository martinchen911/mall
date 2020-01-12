package com.cf.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.PmsProductImage;
import com.cf.mall.bean.PmsProductInfo;
import com.cf.mall.bean.PmsProductSaleAttr;
import com.cf.mall.manage.mapper.PmsProductImageMapper;
import com.cf.mall.manage.mapper.PmsProductInfoMapper;
import com.cf.mall.manage.mapper.PmsProductSaleAttrMapper;
import com.cf.mall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/12
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private PmsProductInfoMapper productInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper productSaleAttrMapper;
    @Autowired
    private PmsProductImageMapper productImageMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        Example e = new Example(PmsProductInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        return productInfoMapper.selectByExample(e);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr saleAttr = new PmsProductSaleAttr();
        saleAttr.setProductId(Long.parseLong(spuId));
        return productSaleAttrMapper.select(saleAttr);
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        PmsProductImage image = new PmsProductImage();
        image.setProductId(Long.parseLong(spuId));
        return productImageMapper.select(image);
    }
}
