package com.cf.mall.user.service;

import com.cf.mall.user.domain.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @Author chen
 * @Date 2019/12/31
 */
public interface MemberAddressService {


    int deleteByPrimaryKey(String id);

    int insert(UmsMemberReceiveAddress record);

    int insertSelective(UmsMemberReceiveAddress record);

    UmsMemberReceiveAddress selectByPrimaryKey(String id);

    List<UmsMemberReceiveAddress> selectByMemberKey(String id);

    List<UmsMemberReceiveAddress> selectAll();

    int updateByPrimaryKeySelective(UmsMemberReceiveAddress record);

    int updateByPrimaryKey(UmsMemberReceiveAddress record);
}
