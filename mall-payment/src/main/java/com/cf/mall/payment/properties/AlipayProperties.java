package com.cf.mall.payment.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里支付配置类
 * @Author chen
 * @Date 2020/3/27
 */
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {

    /**
     * 支付宝网关
     */
    private String url;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 自己的支付编号
     */
    private String appId;

    /**
     * 同步回调地址（浏览器重定向）
     */
    public String returnPaymentUrl;

    /**
     * 异步通知（公网接口webservice）
     */
    public String notifyPaymentUrl;

    /**
     * 订单回调地址
     */
    public String returnOrderUrl;

    /**
     * 传输格式
     */
    public final static String format = "json";
    /**
     * 编码
     */
    public final static String charset = "utf-8";
    /**
     * 密钥格式
     */
    public final static String signType = "RSA2";

    public String getUrl() {
        return url;
    }

    public AlipayProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public AlipayProperties setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public AlipayProperties setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public AlipayProperties setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getReturnPaymentUrl() {
        return returnPaymentUrl;
    }

    public AlipayProperties setReturnPaymentUrl(String returnPaymentUrl) {
        this.returnPaymentUrl = returnPaymentUrl;
        return this;
    }

    public String getNotifyPaymentUrl() {
        return notifyPaymentUrl;
    }

    public AlipayProperties setNotifyPaymentUrl(String notifyPaymentUrl) {
        this.notifyPaymentUrl = notifyPaymentUrl;
        return this;
    }

    public String getReturnOrderUrl() {
        return returnOrderUrl;
    }

    public AlipayProperties setReturnOrderUrl(String returnOrderUrl) {
        this.returnOrderUrl = returnOrderUrl;
        return this;
    }
}
