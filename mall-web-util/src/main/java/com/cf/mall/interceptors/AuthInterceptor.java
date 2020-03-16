package com.cf.mall.interceptors;


import com.cf.mall.annotations.LoginRequired;
import com.cf.mall.util.CookieUtil;
import com.cf.mall.util.HttpclientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 * @author chen
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拦截代码
        // 判断被拦截的请求的访问的方法的注解(是否时需要拦截的)
        HandlerMethod hm = (HandlerMethod)handler;
        LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);
        // 无该注解则不拦截
        if (null == methodAnnotation) {
            return true;
        }

        // 获取token
        String token = "";
        // cookie中获取
        String oldToken = CookieUtil.getCookieValue(request,"oldToken",true);
        if (StringUtils.isNotBlank(oldToken)) {
            token = oldToken;
        }
        // 请求中获取
        String newToken = request.getParameter("token");
        if (StringUtils.isNotBlank(newToken)) {
            token = newToken;
        }


        // 调用认证中心进行验证
        String success = "fail";
        if(StringUtils.isNotBlank(token)){
            success = HttpclientUtil.doGet("http://passport.mall.com:8085/verify?token=" + token);
        }

        if (methodAnnotation.loginSuccess()){
            // 必须登录成功才能使用
            if (!"success".equals(success)) {
                StringBuffer url = request.getRequestURL();
                response.sendRedirect("http://passport.mall.com:8085/index?ReturnUrl="+url);
                return false;
            }
            request.setAttribute("memberId","123");
            request.setAttribute("nikename","oldwang");
            CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);

        } else {
            // 根据登录状态走不同分支
            if ("success".equals(success)) {
                request.setAttribute("memberId","123");
                request.setAttribute("nikename","oldwang");
                CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
            }
        }
        return true;
    }
}
