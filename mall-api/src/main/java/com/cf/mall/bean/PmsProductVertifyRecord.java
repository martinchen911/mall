package com.cf.mall.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * pms_product_vertify_record
 * @author 
 */
@Data
public class PmsProductVertifyRecord implements Serializable {
    private Long id;

    private Long productId;

    private Date createTime;

    /**
     * 审核人
     */
    private String vertifyMan;

    private Integer status;

    /**
     * 反馈详情
     */
    private String detail;

    private static final long serialVersionUID = 1L;
}