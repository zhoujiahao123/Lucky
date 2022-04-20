package com.uestc.luckyuser.redis;

/**
 * @author jacob
 * @date 2022/4/20 9:46
 */
public class UserPrefix extends BasePrefix {

    public UserPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public UserPrefix(String prefix) {
        super(prefix);
    }

    public static UserPrefix getByMobilePhoneNumber = new UserPrefix("MobilePhoneNumber");
    public static UserPrefix getByToken = new UserPrefix("Token");
}
