package com.uestc.luckyuser.redis;

/**
 * @author jacob
 * @date 2022/4/20 9:42
 */
public abstract class BasePrefix implements KeyPrefix {

    //redis中过期时间
    private int expireSeconds;
    //redis中存放的key的前缀
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
