package com.cf.mall.user.controller;

import com.cf.mall.bean.UmsMember;
import com.cf.mall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen
 * 2020/5/25
 */
@RequestMapping("member")
@RestController
public class Membercontroller {

    @Autowired
    MemberService memberService;

    @GetMapping("{id}")
    public UmsMember get(@RequestPart String id) {
        return memberService.selectByPrimaryKey(id);
    }

}
