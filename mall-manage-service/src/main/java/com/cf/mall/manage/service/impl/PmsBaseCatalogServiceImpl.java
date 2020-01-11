package com.cf.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.PmsBaseCatalog1;
import com.cf.mall.bean.PmsBaseCatalog2;
import com.cf.mall.bean.PmsBaseCatalog3;
import com.cf.mall.manage.mapper.PmsBaseCatalog1Mapper;
import com.cf.mall.manage.mapper.PmsBaseCatalog2Mapper;
import com.cf.mall.manage.mapper.PmsBaseCatalog3Mapper;
import com.cf.mall.service.PmsBaseCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2020/1/10
 */
@Service
public class PmsBaseCatalogServiceImpl implements PmsBaseCatalogService {
    @Autowired
    private PmsBaseCatalog1Mapper catalog1Mapper;
    @Autowired
    private PmsBaseCatalog2Mapper catalog2Mapper;
    @Autowired
    private PmsBaseCatalog3Mapper catalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return catalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        Example e = new Example(PmsBaseCatalog2.class);
        e.createCriteria().andEqualTo("catalog1Id",catalog1Id);
        return catalog2Mapper.selectByExample(e);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        Example e = new Example(PmsBaseCatalog3.class);
        e.createCriteria().andEqualTo("catalog2Id",catalog2Id);
        return catalog3Mapper.selectByExample(e);
    }
}
