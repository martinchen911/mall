package com.cf.mall.service;

import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;
import com.cf.mall.bean.PmsBaseSaleAttr;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 平台属性服务
 *
 * @Author chen
 * @Date 2020/1/11
 */
@FeignClient("manage-service")
public interface AttrService {

    /**
     * 属性列表
     * @param catalog3 三级分类id
     * @return
     */
    @PostMapping("/attr/attrInfoList")
    List<PmsBaseAttrInfo> attrInfoList(@RequestParam String catalog3);

    /**
     * 属性值列表
     * @param attrId 属性id
     * @return
     */
    @PostMapping("/attr/getAttrValueList")
    List<PmsBaseAttrValue> getAttrValueList(@RequestParam String attrId);

    /**
     * 保存属性
     * @param attrInfo
     */
    @PostMapping("/attr/saveAttrInfo")
    void saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo);

    /**
     * 销售属性列表
     * @return
     */
    @GetMapping("/attr/baseSaleAttrList")
    List<PmsBaseSaleAttr> baseSaleAttrList();

    /**
     * 获取属性列表
     * @param ids
     */
    @PostMapping("/attr/listAttr")
    List<PmsBaseAttrInfo> listAttr(@RequestBody List<Long> ids);
}
