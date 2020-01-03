package com.cf.mall.user.service.impl;

import com.cf.mall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper memberMapper;

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
