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
    NAME_CANNOT_BLANK(20004,"用户名不能为空"),
    PASSWORD_CANNOT_BLANK(20005,"密码不能为空"),
    PHONE_CANNOT_BLANK(20006,"手机号不能为空"),
    GENDER_CANNOT_BLANK(20007,"性别不能为空"),
    PHONE_NOT_MEET_PASSWORD(20008,"用户名或密码错误"),
    VERIFY_CODE_CANNOT_BLANK(20009,"验证码不能为空"),
    VERIFY_CODE_ERROR(20010,"验证码错误"),
    USER_OR_PASSWORD_EMPTY(20011,"用户名或密码不能为空"),
    CREATE_SELLER_FAIL(20012,"创建商家失败"),
    SELLER_NOTFOUND(20013,"商家不存在"),
    CREATE_CATEGORY_FAIL(200014,"创建商品类型失败"),
    CREATE_SHOP_FAIL(200015,"创建门店失败"),
    SELLER_ALREADY_DISABLE(200016,"商家已被禁用"),
    CATEGORY_NOTFOUND(200017,"类别不存在"),
    LONGITUDE_OR_LATITUDE_CANNOT_EMPTY(200018,"经纬度不能为空"),
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
