package com.uestc.luckyuser.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/18 11:17
 */

public enum ResultCode {
    SUCCESS(200,"操作成功"),
    USER_NOTFOUND(401,"用户不存在"),;
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
