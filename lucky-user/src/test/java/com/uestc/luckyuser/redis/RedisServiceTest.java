package com.uestc.luckyuser.redis;

import com.uestc.luckyuser.LuckyUserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author jacob
 * @date 2022/4/19 16:57
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisServiceTest {

    @Resource
    RedisService redisService;

    @Test
    public void testRedisSet() {
        String key = "key";
        String value = "value";
        redisService.set(UserPrefix.getByMobilePhoneNumber, key, value);
    }

    @Test
    public void testRedisGet() {
        String key = "sdkjfghsdkjfhg";
        String value = redisService.get(UserPrefix.getByMobilePhoneNumber,key, String.class);
        System.out.println(value);
    }
}
