package com.cf.mall.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * oms_cart_item
 * @author 
 */
@Data
@NoArgsConstructor
public class OmsCartItem implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long productSkuId;

    private Long memberId;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 添加到购物车的价格
     */
    private BigDecimal price;

    /**
     * 销售属性1
     */
    private String sp1;

    /**
     * 销售属性2
     */
    private String sp2;

    /**
     * 销售属性3
     */
    private String sp3;

    /**
     * 商品主图
     */
    private String productPic;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品副标题（卖点）
     */
    private String productSubTitle;

    /**
     * 商品sku条码
     */
    private String productSkuCode;

    /**
     * 会员昵称
     */
    private String memberNickname;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 是否删除
     */
    private Integer deleteStatus;

    /**
     * 商品分类
     */
    private Long productCategoryId;

    /**
     * 产品品牌
     */
    private String productBrand;

    /**
     * 商品编号
     */
    private String productSn;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttr;

    /**
     * 是否选中
     */
    private String isChecked;

    private static final long serialVersionUID = 1L;

    public OmsCartItem(PmsSkuInfo sku,Integer quantity,Long memberId) {
        BeanUtils.copyProperties(sku,this);
        this.id = null;
        this.productSkuId = sku.getId();
        this.memberId = memberId;
        this.quantity = quantity;
        this.price = new BigDecimal(sku.getPrice());
        // 销售属性
        if (null != sku.getSkuSaleAttrValueList() && sku.getSkuSaleAttrValueList().size() > 0) {
            this.sp1 = sku.getSkuSaleAttrValueList().get(0).getId().toString();
            if (sku.getSkuSaleAttrValueList().size() > 1) {
                this.sp2 = sku.getSkuSaleAttrValueList().get(1).getId().toString();
            }
            if (sku.getSkuSaleAttrValueList().size() > 2) {
                this.sp3 = sku.getSkuSaleAttrValueList().get(2).getId().toString();
            }
        }
        // 商品主图
        this.productPic = sku.getSkuDefaultImg();
        // 商品名称
        this.productName = sku.getSkuName();
        // 副标题
        this.productSubTitle = sku.getSkuDesc();
        // 创建时间
        //this.createDate = new Date();
        // 修改时间
        //this.modifyDate = new Date();
        // 三级分类
        this.productCategoryId = sku.getCatalog3Id();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmsCartItem cartItem = (OmsCartItem) o;


        return Objects.equals(productId, cartItem.productId) &&
                Objects.equals(productSkuId, cartItem.productSkuId) &&
                (Objects.equals(sp1, cartItem.sp1) || sp1 == cartItem.sp1) &&
                (Objects.equals(sp2, cartItem.sp2) || sp2 == cartItem.sp2) &&
                (Objects.equals(sp3, cartItem.sp3) || sp3 == cartItem.sp3) ;
    }


}