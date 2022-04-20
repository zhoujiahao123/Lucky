package com.uestc.luckyuser.redis;

/**
 * @author jacob
 * @date 2022/4/20 9:41
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
