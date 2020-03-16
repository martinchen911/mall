package com.cf.mall.passport.controller;

import com.cf.mall.bean.UmsMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author chen
 * @Date 2020/3/15
 */
@Controller
public class PassportController {




    @GetMapping("verify")
    @ResponseBody
    public String verify(String token){

        return "success";
    }

    @PostMapping("login")
    @ResponseBody
    public String login(UmsMember member){

        return "token";
    }


    @GetMapping("index")
    public String index(String ReturnUrl, ModelMap map){
        map.put("ReturnUrl",ReturnUrl);
        return "index";
    }

}
