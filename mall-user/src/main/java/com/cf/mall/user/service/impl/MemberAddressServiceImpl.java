package com.cf.mall.user.service.impl;

import com.cf.mall.user.domain.UmsMemberReceiveAddress;
import com.cf.mall.user.mapper.UmsMemberReceiveAddressMapper;
import com.cf.mall.user.service.MemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
@Service
public abstract class MemberAddressServiceImpl implements MemberAddressService {

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
    public List<UmsMemberReceiveAddress> selectByMemberKey(String id) {
        Example<UmsMemberReceiveAddress> example = new Example<UmsMemberReceiveAddress>() {
            @Override
            public UmsMemberReceiveAddress getProbe() {
                UmsMemberReceiveAddress address = new UmsMemberReceiveAddress();
                address.setMemberId(Long.parseLong(id));
                return address;
            }

            @Override
            public ExampleMatcher getMatcher() {
                ExampleMatcher matcher = ExampleMatcher.matching();
                matcher.
                return null;
            }
        };
        return addressMapper.selectByExample(example);
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
