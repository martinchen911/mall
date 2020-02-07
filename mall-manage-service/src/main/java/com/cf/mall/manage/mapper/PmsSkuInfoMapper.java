package com.cf.mall.manage.mapper;

import com.cf.mall.bean.PmsSkuInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {


    List<PmsSkuInfo> selectSkuValue(Long productId);
}