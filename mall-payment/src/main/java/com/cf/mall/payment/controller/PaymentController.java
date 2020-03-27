package com.cf.mall.payment.controller;


import com.cf.mall.annotations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author chen
 * @Date 2020/3/26
 */
@Controller
public class PaymentController {



    @LoginRequired
    @RequestMapping("wechart/submit")
    public String wechart(String outTradeNo, String totalAmount, ModelMap map, HttpServletRequest request) {

        return null;
    }


    @LoginRequired
    @RequestMapping("index")
    public String index(String outTradeNo, String totalAmount, ModelMap map, HttpServletRequest request) {
        String nickName = String.valueOf(request.getAttribute("nickName"));

        map.put("outTradeNo",outTradeNo);
        map.put("totalAmount",totalAmount);
        map.put("nickName",nickName);

        return "index";
    }


}
