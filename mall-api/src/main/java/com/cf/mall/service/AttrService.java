package com.cf.mall.service;

import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;
import com.cf.mall.bean.PmsBaseSaleAttr;

import java.util.List;

/**
 * 平台属性服务
 *
 * @Author chen
 * @Date 2020/1/11
 */
public interface AttrService {

    /**
     * 属性列表
     * @param catalog3 三级分类id
     * @return
     */
    List<PmsBaseAttrInfo> attrInfoList(String catalog3);

    /**
     * 属性值列表
     * @param attrId 属性id
     * @return
     */
    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    /**
     * 保存属性
     * @param attrInfo
     */
    void saveAttrInfo(PmsBaseAttrInfo attrInfo);

    /**
     * 销售属性列表
     * @return
     */
    List<PmsBaseSaleAttr> baseSaleAttrList();

    /**
     * 获取属性列表
     * @param ids
     */
    List<PmsBaseAttrInfo> listAttr(List<Long> ids);
}
