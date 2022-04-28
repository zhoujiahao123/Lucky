package com.uestc.luckyuser.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/18 11:17
 */

public enum ResultCode {
    SUCCESS(200,"操作成功"),

    //10000，通用的
    PARAMETER_VALIDATION_ERROR(100001,"参数校验失败"),


    //20000，与用户相关的错误提示
    USER_NOTFOUND(20001,"用户不存在"),
    USER_LOG_UP_FAILED(20002,"用户注册失败"),
    MOBILE_PHONE_NUMBER_EXIST(20003,"手机号已经注册"),
    PHONE_NOT_MEET_PASSWORD(20008,"用户名或密码错误"),
    VERIFY_CODE_CANNOT_BLANK(20009,"验证码不能为空"),
    VERIFY_CODE_ERROR(20010,"验证码错误"),
    USER_OR_PASSWORD_EMPTY(20011,"用户名或密码不能为空"),
    LONGITUDE_OR_LATITUDE_CANNOT_EMPTY(200012,"经纬度不能为空"),

    //30000，与商家相关的错误提示
    CREATE_SELLER_FAIL(30001,"创建商家失败"),
    SELLER_NOTFOUND(30002,"商家不存在"),
    SELLER_ALREADY_DISABLE(30003,"商家已被禁用"),

    //40000，与类别相关的错误提示
    CREATE_CATEGORY_FAIL(40001,"创建类别失败"),
    CATEGORY_NOTFOUND(40001,"类别不存在"),


    //50000,与门店相关的错误提示
    CREATE_SHOP_FAIL(50001,"创建门店失败"),
    ;


    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
