package com.uestc.luckyuser.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author jacob
 * @date 2022/4/19 16:54
 */
@Service
public class RedisService {

    @Resource
    JedisPool jedisPool;

    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        //key 不允许为空
        if (key == null || key.isEmpty()) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //转化为string类型存在redis中
            String str = beanToString(value);
            //将key添加上对应的前缀
            String realKey = prefix.getPrefix() + key;
            //过期时间
            int expire = prefix.expireSeconds();
            if (expire > 0) {
                jedis.setex(realKey, expire, str);
            } else {
                jedis.set(realKey, str);
            }
            return true;
        } finally {
            //回收jedis连接到jedispool中
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz) {
        if (key == null || key.isEmpty()) return null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String valueString = jedis.get(realKey);
            T value = stringToBean(valueString, clazz);
            return value;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    /**
     * 将输入{value} 转化为 {string}
     *
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 利用fastjson
     * 将{value} 按其本身类型 转化为 对应的java对象
     *
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T stringToBean(String value, Class<T> clazz) {
        if (value == null || value.isEmpty() || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(value);
        } else if (clazz == String.class) {
            return (T) value;
        } else {
            return JSON.toJavaObject(JSON.parseObject(value), clazz);
        }
    }
}
