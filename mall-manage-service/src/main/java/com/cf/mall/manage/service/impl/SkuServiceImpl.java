package com.cf.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.manage.mapper.PmsSkuAttrValueMapper;
import com.cf.mall.manage.mapper.PmsSkuImageMapper;
import com.cf.mall.manage.mapper.PmsSkuInfoMapper;
import com.cf.mall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.cf.mall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author chen
 * @Date 2020/1/15
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private PmsSkuInfoMapper infoMapper;
    @Autowired
    private PmsSkuImageMapper imageMapper;
    @Autowired
    private PmsSkuAttrValueMapper attrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper saleAttrValueMapper;



    @Override
    public void saveSkuInfo(PmsSkuInfo skuInfo) {
        // 保存 sku 基本信息
        infoMapper.insertSelective(skuInfo);

        // 保存 sku 图片
        skuInfo.getSkuImageList().forEach(img -> {
            img.setSkuId(skuInfo.getId());
            imageMapper.insertSelective(img);
        });
        // 保存 sku 平台属性值
        skuInfo.getSkuAttrValueList().forEach(v -> {
            v.setSkuId(skuInfo.getId());
            attrValueMapper.insertSelective(v);
        });
        // 保存 sku 销售属性值
        skuInfo.getSkuSaleAttrValueList().forEach(v -> {
            v.setSkuId(skuInfo.getId());
            saleAttrValueMapper.insertSelective(v);
        });

    }
}
