package com.cf.mall.manage.service.impl;

import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;
import com.cf.mall.bean.PmsBaseSaleAttr;
import com.cf.mall.manage.mapper.PmsBaseAttrInfoMapper;
import com.cf.mall.manage.mapper.PmsBaseAttrValueMapper;
import com.cf.mall.manage.mapper.PmsBaseSaleAttrMapper;
import com.cf.mall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/11
 */
@RequestMapping("attr")
@RestController
public class AttrServiceImpl implements AttrService {

    @Autowired
    private PmsBaseAttrInfoMapper attrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper attrValueMapper;
    @Autowired
    private PmsBaseSaleAttrMapper saleAttrMapper;

    @PostMapping("attrInfoList")
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(@RequestParam String catalog3) {
        Example e = new Example(PmsBaseAttrInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3);
        List<PmsBaseAttrInfo> infos = attrInfoMapper.selectByExample(e);

        infos.forEach(x -> {
            PmsBaseAttrValue value = new PmsBaseAttrValue();
            value.setAttrId(x.getId());
            x.setAttrValueList(attrValueMapper.select(value));
        });
        return infos;
    }
    @PostMapping("getAttrValueList")
    @Override
    public List<PmsBaseAttrValue> getAttrValueList(@RequestParam String attrId) {
        Example e = new Example(PmsBaseAttrValue.class);
        e.createCriteria().andEqualTo("attrId",attrId);
        return attrValueMapper.selectByExample(e);
    }

    @PostMapping("saveAttrInfo")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo) {
        if (attrInfo.getId() == null) {
            attrInfoMapper.insertSelective(attrInfo);
        } else {
            attrInfoMapper.updateByPrimaryKey(attrInfo);
            PmsBaseAttrValue attrValue = new PmsBaseAttrValue();
            attrValue.setAttrId(attrInfo.getId());
            attrValueMapper.delete(attrValue);
        }
        attrInfo.getAttrValueList().forEach(v -> {
            v.setAttrId(attrInfo.getId());
            attrValueMapper.insertSelective(v);
        });
    }

    @GetMapping("baseSaleAttrList")
    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return saleAttrMapper.selectAll();
    }

    @PostMapping("listAttr")
    @Override
    public List<PmsBaseAttrInfo> listAttr(@RequestBody List<Long> ids) {
        return attrInfoMapper.selectAttrByValueIds(ids);
    }
}
