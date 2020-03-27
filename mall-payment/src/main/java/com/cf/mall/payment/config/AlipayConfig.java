package com.cf.mall.payment.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.cf.mall.payment.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 支付配置
 * @author chen
 */
@Configuration
public class AlipayConfig {

    @Autowired
    AlipayProperties alipayProperties;
    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getPrivateKey(),
                AlipayProperties.format,
                AlipayProperties.charset,
                alipayProperties.getPublicKey(),
                AlipayProperties.signType);
        return alipayClient;
    }
}