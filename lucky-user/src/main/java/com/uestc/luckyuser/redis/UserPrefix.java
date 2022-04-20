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

    public static final UserPrefix getByMobilePhoneNumber = new UserPrefix("MobilePhoneNumber");
    public static final UserPrefix getByToken = new UserPrefix("Token");
    public static final UserPrefix VerifyTimes = new UserPrefix("verifyTimes");
    public static final UserPrefix VerifyCode = new UserPrefix("verifyCode");
}
