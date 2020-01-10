package com.cf.mall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.mall.bean.UmsMember;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import com.cf.mall.service.MemberService;
import com.cf.mall.user.mapper.UmsMemberMapper;
import com.cf.mall.user.mapper.UmsMemberReceiveAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private UmsMemberReceiveAddressMapper addressMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UmsMember record) {
        return memberMapper.insert(record);
    }

    @Override
    public int insertSelective(UmsMember record) {
        return memberMapper.insertSelective(record);
    }

    @Override
    public UmsMember selectByPrimaryKey(String id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsMemberReceiveAddress> selectByMemberKey(String memberId) {
        Example e = new Example(UmsMemberReceiveAddress.class);
        e.createCriteria().andEqualTo("memberId",memberId);
        return addressMapper.selectByExample(e);
    }

    @Override
    public List<UmsMember> selectAll() {
        return memberMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(UmsMember record) {
        return memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UmsMember record) {
        return memberMapper.updateByPrimaryKey(record);
    }
}
