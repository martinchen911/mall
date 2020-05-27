package com.cf.mall.manage.service.impl;

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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author chen
 * @Date 2020/1/15
 */
@RequestMapping("sku")
@RestController
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
    @Autowired
    private RedissonClient redisson;



    @PostMapping("saveSkuInfo")
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

    @GetMapping("getSku")
    @Override
    public PmsSkuInfo getSku(String id) {
        // 生成 key
        String key = "PmsSkuInfo:"+id+":getSku";

        // 获取锁
        RLock lock = redisson.getLock("anyLock");

        // 1.连接缓存(自动资源释放)
        try(Jedis jedis = redisUtil.getJedis()) {
            // 2.查询缓存
            String s = jedis.get(key);
            if (StringUtils.isNoneBlank(s)) {
                return JSON.parseObject(s,PmsSkuInfo.class);
            } else {
                lock.lock(3, TimeUnit.SECONDS);

                // 3.如果没有，则查询mysql
                PmsSkuInfo info = getSkuDB(id);
                // 4.查询结果存入redis
                if (null == info) {
                    // 防止缓存穿透，存储空值一段时间
                    jedis.setex(key,3*60,JSON.toJSONString(info));
                } else {
                    jedis.set(key,JSON.toJSONString(info));
                }

                return info;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
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

    @GetMapping("listSku")
    @Override
    public List<PmsSkuInfo> listSku(Long productId) {
        return infoMapper.selectSkuValue(productId);
    }

    @GetMapping("listSku1")
    @Override
    public List<PmsSkuInfo> listSku() {
        List<PmsSkuInfo> skuInfos = infoMapper.selectAll();
        skuInfos.forEach(s -> {
            PmsSkuAttrValue attrValue = new PmsSkuAttrValue();
            attrValue.setSkuId(s.getId());
            s.setSkuAttrValueList(attrValueMapper.select(attrValue));
        });
        return skuInfos;
    }

    @GetMapping("checkPrice")
    @Override
    public boolean checkPrice(Long id, BigDecimal price) {
        boolean flag = false;

        PmsSkuInfo skuInfo = infoMapper.selectByPrimaryKey(id);
        if (null != skuInfo && new BigDecimal(String.valueOf(skuInfo.getPrice())).equals(price)) {
            flag = true;
        }
        return flag;
    }


}
