package com.cf.mall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.UmsMember;
import com.cf.mall.service.MemberService;
import com.cf.mall.util.HttpclientUtil;
import com.cf.mall.util.JwtUtil;
import com.cf.mall.util.RequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chen
 * @Date 2020/3/20
 */
@Controller
public class SocialSignController {

    @Value("${global-constant.security.key}")
    private String jwtKey;
    @Value("${global-constant.app.key}")
    private String key;
    @Value("${global-constant.app.secret}")
    private String secret;

    @Reference
    private MemberService memberService;


    @GetMapping("vlogout")
    @ResponseBody
    public void vlogout() {
    }

    @GetMapping("vlogin")
    public String vlogin(String code, HttpServletRequest request) {
        // 1.通过 token 换取 access_tooken
        Map<String,String> accessMap = getAccess(code);

        // 2.通过 access_token 和 uuid 获取授权用户数据
        Map<String,String> member = getMember(accessMap);

        // 3.将根据用户信息创建账户
        UmsMember um = new UmsMember();
        um.setSourceType("1");
        um.setAccessCode(code);
        um.setAccessToken(accessMap.get("access_token"));
        um.setSourceUid(member.get("idstr"));
        um.setCity(member.get("location"));

        um.setNickname(member.get("name"));
        memberService.insertSocial(um);

        // 制作 token
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("memberId",um.getId());
        userMap.put("nickname",um.getNickname());
        String token = JwtUtil.encode(jwtKey,userMap, RequestUtil.getIpAddress(request));

        // 缓存 token
        memberService.setMemberToken(um.getId(),token);
        return "redirect:http://search.mall.com:8083/index?token="+token;
    }

    private Map<String, String> getMember(Map<String,String> map) {
        String url = "https://api.weibo.com/2/users/show.json?" +
                "access_token=" + map.get("access_token") +
                "&uid=" + map.get("uid");
        String s = HttpclientUtil.doGet(url);
        return JSON.parseObject(s, HashMap.class);
    }

    private Map<String, String> getAccess(String code) {
        String url = "https://api.weibo.com/oauth2/access_token";
        Map<String,String> param = new HashMap<>(6);
        param.put("client_id",key);
        param.put("client_secret",secret);
        param.put("grant_type","authorization_code");
        param.put("redirect_uri","http://passport.mall.com:8085/vlogin");
        param.put("code",code);

        String s = HttpclientUtil.doPost(url,param);

        return JSON.parseObject(s,HashMap.class);
    }




}
