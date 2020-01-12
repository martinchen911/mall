package com.cf.mall.service;


import com.cf.mall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * 地址服务
 *
 * @Author chen
 * @Date 2019/12/31
 */
public interface MemberAddressService {


    int deleteByPrimaryKey(String id);

    int insert(UmsMemberReceiveAddress record);

    int insertSelective(UmsMemberReceiveAddress record);

    UmsMemberReceiveAddress selectByPrimaryKey(String id);

    List<UmsMemberReceiveAddress> selectAll();

    int updateByPrimaryKeySelective(UmsMemberReceiveAddress record);

    int updateByPrimaryKey(UmsMemberReceiveAddress record);
}
