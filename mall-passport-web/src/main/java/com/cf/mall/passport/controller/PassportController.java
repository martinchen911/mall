package com.cf.mall.passport.controller;

import com.cf.mall.bean.UmsMember;
import com.cf.mall.service.MemberService;
import com.cf.mall.util.JwtUtil;
import com.cf.mall.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chen
 * @Date 2020/3/15
 */
@Controller
public class PassportController {

    @Value("${global-constant.security.key}")
    private String key;

    @Autowired
    private MemberService memberService;



    @GetMapping("verify")
    @ResponseBody
    public Map<String, String> verify(String token, String salt){
        Map<String, String> map = new HashMap<>(5);
        Map<String,Object> tkMap = JwtUtil.decode(token, this.key,salt);
        if (null != tkMap) {
            map.put("status","success");
            map.put("memberId",String.valueOf(tkMap.get("memberId")));
            map.put("nickName",String.valueOf(tkMap.get("nickName")));
        } else {
            map.put("status","fail");
        }
        return map;
    }

    @PostMapping("login")
    @ResponseBody
    public String login(UmsMember member, HttpServletRequest request){
        String token = "fail";

        UmsMember umsMember = memberService.login(member);

        // 登录成功
        if (null != umsMember) {
            // 准备私有数据
            Map<String,Object> tkMap = new HashMap<>(4);
            tkMap.put("memberId",umsMember.getId());
            tkMap.put("nickName",umsMember.getNickname());

            // 准备salt
            String salt = RequestUtil.getIpAddress(request);

            // jwt 制作 token
            token = JwtUtil.encode(this.key,tkMap,salt);

            memberService.setMemberToken(umsMember.getId(),token);
        }
        return token;
    }

    @GetMapping("index")
    public String index(String ReturnUrl, ModelMap map){
        map.put("ReturnUrl",ReturnUrl);
        return "index";
    }

}
