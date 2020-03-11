package com.cf.mall.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * 搜索商品信息
 * @Author chen
 * @Date 2020/3/6
 */
@ToString
@NoArgsConstructor
@Data
public class PmsSearchSkuInfo implements Serializable {

    public PmsSearchSkuInfo (PmsSkuInfo skuInfo) {
        BeanUtils.copyProperties(skuInfo,this);
        this.id = String.valueOf(skuInfo.getId());
        this.productId = String.valueOf(skuInfo.getProductId());
        this.catalog3Id = String.valueOf(skuInfo.getCatalog3Id());

    }

    @Id
    private String id;

    /**
     * sku名称
     */
    private String skuName;
    /**
     * 商品规格描述
     */
    private String skuDesc;
    /**
     * 三级分类
     */
    private String catalog3Id;
    /**
     * 价格
     */
    private Double price;
    /**
     * 默认图片
     */
    private String skuDefaultImg;
    /**
     * 热度
     */
    private Double hotScore;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 平台属性集合
     */
    private List<PmsSkuAttrValue> skuAttrValueList;


}
