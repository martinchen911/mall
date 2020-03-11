package com.cf.mall.manage.mapper;

import com.cf.mall.bean.PmsBaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {

    List<PmsBaseAttrInfo> selectAttrByValueIds(List<Long> ids);
}