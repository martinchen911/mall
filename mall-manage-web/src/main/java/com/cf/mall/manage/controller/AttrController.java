package com.cf.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;
import com.cf.mall.bean.PmsBaseSaleAttr;
import com.cf.mall.service.AttrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台属性
 *
 * @Author chen
 * @Date 2020/1/11
 */
@RestController
@CrossOrigin
public class AttrController {

    @Reference
    private AttrService attrService;

    @GetMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        return attrService.attrInfoList(catalog3Id);
    }

    @PostMapping("/getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        return attrService.getAttrValueList(attrId);
    }

    @PostMapping("/saveAttrInfo")
    public void saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo) {
        attrService.saveAttrInfo(attrInfo);
    }

    @PostMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return attrService.baseSaleAttrList();
    }


}
