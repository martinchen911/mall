package com.cf.mall.service;


import com.cf.mall.bean.UmsMember;
import com.cf.mall.bean.UmsMemberReceiveAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 *
 * @Author chen
 * @Date 2019/12/31
 */
@FeignClient("user-service")
public interface MemberService {

    @DeleteMapping("/member/deleteByPrimaryKey")
    int deleteByPrimaryKey(@RequestParam String id);

    @PostMapping("/member/insert")
    int insert(@RequestBody UmsMember record);

    @PostMapping("/member/insertSelective")
    int insertSelective(@RequestBody UmsMember record);

    @PostMapping("/member/selectByPrimaryKey")
    UmsMember selectByPrimaryKey(@RequestParam String id);

    @PostMapping("/member/selectByMemberKey")
    List<UmsMemberReceiveAddress> selectByMemberKey(@RequestParam String id);

    @GetMapping("/member/selectAll")
    List<UmsMember> selectAll();

    @PutMapping("/member/updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(@RequestBody UmsMember record);

    @PutMapping("/member/updateByPrimaryKey")
    int updateByPrimaryKey(@RequestBody UmsMember record);

    @PostMapping("/member/login")
    UmsMember login(@RequestBody UmsMember member);

    @PutMapping("/member/setMemberToken")
    void setMemberToken(@RequestParam("id") Long id,@RequestParam("token") String token);

    @PostMapping("/member/insertSocial")
    void insertSocial(@RequestBody UmsMember um);

    @PostMapping("/member/getAddress")
    UmsMemberReceiveAddress getAddress(@RequestParam String addressId);
}
