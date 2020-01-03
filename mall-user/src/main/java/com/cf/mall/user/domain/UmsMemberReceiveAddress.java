package com.cf.mall.user.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * ums_member_receive_address
 * @author 
 */
@Data
public class UmsMemberReceiveAddress implements Serializable {
    private Long id;

    private Long memberId;

    /**
     * 收货人名称
     */
    private String name;

    private String phoneNumber;

    /**
     * 是否为默认
     */
    private Integer defaultStatus;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 省份/直辖市
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址(街道)
     */
    private String detailAddress;

    private static final long serialVersionUID = 1L;
}