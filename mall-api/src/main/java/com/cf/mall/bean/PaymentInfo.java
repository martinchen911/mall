package com.cf.mall.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * payment_info
 * @author 
 */
@Data
public class PaymentInfo implements Serializable {
    /**
     * 编号
     */
    private Long id;

    /**
     * 对外业务编号
     */
    private String orderSn;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 支付宝交易编号
     */
    private String alipayTradeNo;

    /**
     * 支付金额
     */
    private BigDecimal totalAmount;

    /**
     * 交易内容
     */
    private String subject;

    /**
     * 支付状态
     */
    private String paymentStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date confirmTime;

    /**
     * 回调信息
     */
    private String callbackContent;

    private Date callbackTime;

    private static final long serialVersionUID = 1L;
}