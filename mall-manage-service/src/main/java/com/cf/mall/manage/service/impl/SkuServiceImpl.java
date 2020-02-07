package com.cf.mall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.cf.mall.bean.PmsSkuAttrValue;
import com.cf.mall.bean.PmsSkuImage;
import com.cf.mall.bean.PmsSkuInfo;
import com.cf.mall.bean.PmsSkuSaleAttrValue;
import com.cf.mall.manage.mapper.PmsSkuAttrValueMapper;
import com.cf.mall.manage.mapper.PmsSkuImageMapper;
import com.cf.mall.manage.mapper.PmsSkuInfoMapper;
import com.cf.mall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.cf.mall.service.SkuService;
import com.cf.mall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @Author chen
 * @Date 2020/1/15
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private PmsSkuInfoMapper infoMapper;
    @Autowired
    private PmsSkuImageMapper imageMapper;
    @Autowired
    private PmsSkuAttrValueMapper attrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper saleAttrValueMapper;
    @Autowired
    private RedisUtil redisUtil;



    @Override
    public void saveSkuInfo(PmsSkuInfo skuInfo) {
        // 保存 sku 基本信息
        infoMapper.insertSelective(skuInfo);

        // 保存 sku 图片
        skuInfo.getSkuImageList().forEach(img -> {
            img.setSkuId(skuInfo.getId());
            imageMapper.insertSelective(img);
        });
        // 保存 sku 平台属性值
        skuInfo.getSkuAttrValueList().forEach(v -> {
            v.setSkuId(skuInfo.getId());
            attrValueMapper.insertSelective(v);
        });
        // 保存 sku 销售属性值
        skuInfo.getSkuSaleAttrValueList().forEach(v -> {
            v.setSkuId(skuInfo.getId());
            saleAttrValueMapper.insertSelective(v);
        });

    }

    @Override
    public PmsSkuInfo getSku(String id) {
        String key = "PmsSkuInfo:"+id+":getSku";

        // 1.连接缓存(自动资源释放)
        try(Jedis jedis = redisUtil.getJedis()) {
            // 2.查询缓存
            String s = jedis.get(key);
            if (StringUtils.isNoneBlank(s)) {
                return JSON.parseObject(s,PmsSkuInfo.class);
            } else {
                // redis 分布式锁
                String token = UUID.randomUUID().toString();
                String result = jedis.set(key + "lock", token, "nx", "px", 2*1000);

                if ("ok".equalsIgnoreCase(result)) {
                    // 3.如果没有，则查询mysql
                    PmsSkuInfo info = getSkuDB(id);
                    // 4.查询结果存入redis
                    if (null == info) {
                        // 防止缓存穿透，存储空值一段时间
                        jedis.setex(key,3*60,JSON.toJSONString(info));
                    } else {
                        jedis.set(key,JSON.toJSONString(info));
                    }
                    // lua 脚本
                    String script ="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    // 释放锁
                    jedis.eval(script, Collections.singletonList(key + "lock"),Collections.singletonList(token));

                    return info;
                } else {
                    Thread.sleep(1000);
                    return getSku(id);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public PmsSkuInfo getSkuDB(String id) {
        PmsSkuInfo skuInfo = infoMapper.selectByPrimaryKey(id);

        PmsSkuImage image = new PmsSkuImage();
        image.setSkuId(Long.parseLong(id));
        skuInfo.setSkuImageList(imageMapper.select(image));

        PmsSkuAttrValue attrValue = new PmsSkuAttrValue();
        attrValue.setSkuId(Long.parseLong(id));
        skuInfo.setSkuAttrValueList(attrValueMapper.select(attrValue));

        PmsSkuSaleAttrValue saleAttrValue = new PmsSkuSaleAttrValue();
        saleAttrValue.setSkuId(Long.parseLong(id));
        skuInfo.setSkuSaleAttrValueList(saleAttrValueMapper.select(saleAttrValue));
        return skuInfo;
    }

    @Override
    public List<PmsSkuInfo> listSku(Long productId) {
        return infoMapper.selectSkuValue(productId);
    }


}
