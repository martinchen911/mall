package com.cf.mall.user.controller;


import com.cf.mall.user.service.MemberAddressService;
import com.cf.mall.user.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author chen
 * @Date 2020/1/3
 */
@RestController
@RequestMapping("/address")
public class MemberAddressController {


    @Autowired
    private MemberAddressService addressService;


    @GetMapping("/{id}")
    public Result get(@PathVariable("id") String id) {
        try {
            return Result.Success(addressService.selectByPrimaryKey(id));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @GetMapping()
    public Result list() {
        try {
            return Result.Success(addressService.selectAll());
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }

    @PostMapping()
    public Result save(@RequestBody UmsMemberReceiveAddress member) {
        try {
            return Result.Success(addressService.insertSelective(member));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            return Result.Success(addressService.deleteByPrimaryKey(id));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
    @PutMapping()
    public Result put(@RequestBody UmsMemberReceiveAddress member) {
        try {
            return Result.Success(addressService.updateByPrimaryKeySelective(member));
        } catch (Exception e) {
            return Result.Fail(e.getMessage());
        }
    }
}
