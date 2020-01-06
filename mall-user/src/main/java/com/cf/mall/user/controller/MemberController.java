package com.cf.mall.user.controller;

import com.cf.mall.bean.UmsMember;
import com.cf.mall.service.MemberService;
import com.cf.mall.user.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author chen
 * @Date 2019/12/31
 */
@RestController
@RequestMapping("/user")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/{id}")
    public Result get(@PathVariable("id") String id) {
        try {
            return Result.Success(memberService.selectByPrimaryKey(id));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @GetMapping("/address/{id}")
    public Result listAddress(@PathVariable("id") String id) {
        try {
            return Result.Success(memberService.selectByMemberKey(id));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @GetMapping()
    public Result list() {
        try {
            return Result.Success(memberService.selectAll());
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }

    @PostMapping()
    public Result save(@RequestBody UmsMember member) {
        try {
            return Result.Success(memberService.insertSelective(member));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            return Result.Success(memberService.deleteByPrimaryKey(id));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @PutMapping()
    public Result put(@RequestBody UmsMember member) {
        try {
            return Result.Success(memberService.updateByPrimaryKeySelective(member));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }


}
