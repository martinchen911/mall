package com.cf.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.PmsBaseAttrInfo;
import com.cf.mall.bean.PmsBaseAttrValue;
import com.cf.mall.manage.mapper.PmsBaseAttrInfoMapper;
import com.cf.mall.manage.mapper.PmsBaseAttrValueMapper;
import com.cf.mall.service.PmsBaseAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/11
 */
@Service
public class PmsBaseAttrServiceImpl implements PmsBaseAttrService {

    @Autowired
    private PmsBaseAttrInfoMapper attrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper attrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3) {
        Example e = new Example(PmsBaseAttrInfo.class);
        e.createCriteria().andEqualTo("catalog3Id",catalog3);
        return attrInfoMapper.selectByExample(e);
    }
    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        Example e = new Example(PmsBaseAttrValue.class);
        e.createCriteria().andEqualTo("attrId",attrId);
        return attrValueMapper.selectByExample(e);
    }
}
