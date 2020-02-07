package com.cf.mall;

import com.cf.mall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallManageServiceApplicationTests {

    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void contextLoads() {
        try {
            Jedis jedis = redisUtil.getJedis();
            jedis.set("test","text_value");
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }

}
