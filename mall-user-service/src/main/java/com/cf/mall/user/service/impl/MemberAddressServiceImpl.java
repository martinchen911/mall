package com.cf.mall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import com.cf.mall.service.MemberAddressService;
import com.cf.mall.user.mapper.UmsMemberReceiveAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
@Service
public class MemberAddressServiceImpl implements MemberAddressService {

    @Autowired
    private UmsMemberReceiveAddressMapper addressMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return addressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UmsMemberReceiveAddress record) {
        return addressMapper.insert(record);
    }

    @Override
    public int insertSelective(UmsMemberReceiveAddress record) {
        return addressMapper.insertSelective(record);
    }

    @Override
    public UmsMemberReceiveAddress selectByPrimaryKey(String id) {
        return addressMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<UmsMemberReceiveAddress> selectAll() {
        return addressMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(UmsMemberReceiveAddress record) {
        return addressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UmsMemberReceiveAddress record) {
        return addressMapper.updateByPrimaryKey(record);
    }
}
