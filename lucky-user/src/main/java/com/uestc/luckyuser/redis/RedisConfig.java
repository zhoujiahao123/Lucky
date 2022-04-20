package com.uestc.luckyuser.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author jacob
 * @date 2022/4/19 16:42
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int pollMaxIdle;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int pollMaxTotal;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int pollMinIdel;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(pollMaxIdle);
        poolConfig.setMaxTotal(pollMaxTotal);
        poolConfig.setMinIdle(pollMinIdel);
        JedisPool jp = new JedisPool(poolConfig,host,port,timeout);
        return jp;
    }
}
