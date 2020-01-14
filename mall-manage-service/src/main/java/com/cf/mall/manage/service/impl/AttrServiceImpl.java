package com.cf.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;
import com.cf.mall.bean.PmsBaseSaleAttr;
import com.cf.mall.manage.mapper.PmsBaseAttrInfoMapper;
import com.cf.mall.manage.mapper.PmsBaseAttrValueMapper;
import com.cf.mall.manage.mapper.PmsBaseSaleAttrMapper;
import com.cf.mall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/11
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private PmsBaseAttrInfoMapper attrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper attrValueMapper;
    @Autowired
    private PmsBaseSaleAttrMapper saleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3) {
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
    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        Example e = new Example(PmsBaseAttrValue.class);
        e.createCriteria().andEqualTo("attrId",attrId);
        return attrValueMapper.selectByExample(e);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttrInfo(PmsBaseAttrInfo attrInfo) {
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

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return saleAttrMapper.selectAll();
    }
}
