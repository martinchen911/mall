package com.cf.mall.service;


import com.cf.mall.bean.UmsMember;
import com.cf.mall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * 用户服务
 *
 * @Author chen
 * @Date 2019/12/31
 */
public interface MemberService {


    int deleteByPrimaryKey(String id);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    UmsMember selectByPrimaryKey(String id);

    List<UmsMemberReceiveAddress> selectByMemberKey(String id);

    List<UmsMember> selectAll();

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);

    UmsMember login(UmsMember member);

    void setMemberToken(Long id, String token);

    void insertSocial(UmsMember um);
}
