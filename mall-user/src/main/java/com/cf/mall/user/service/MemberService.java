package com.cf.mall.user.service;

import com.cf.mall.user.domain.UmsMember;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
public interface MemberService {


    int deleteByPrimaryKey(String id);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    UmsMember selectByPrimaryKey(String id);

    List<UmsMember> selectAll();

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);
}
