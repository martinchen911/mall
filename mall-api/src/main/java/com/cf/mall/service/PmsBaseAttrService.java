package com.cf.mall.service;

import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/11
 */
public interface PmsBaseAttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
