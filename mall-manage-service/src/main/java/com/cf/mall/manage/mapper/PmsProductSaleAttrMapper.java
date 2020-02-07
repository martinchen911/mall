package com.cf.mall.manage.mapper;

import com.cf.mall.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {

    /**
     * 查询 spu 属性列表
     * @param spuId
     * @param skuId
     * @return
     */
    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("spuId") Long spuId, @Param("skuId") Long skuId);

}