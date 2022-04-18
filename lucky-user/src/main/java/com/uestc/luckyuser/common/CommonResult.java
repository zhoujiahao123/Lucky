package com.uestc.luckyuser.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/18 10:44
 */
@Data
@AllArgsConstructor
public class CommonResult<T> {

    //返回给前端的代码
    private long code;
    //返回给前端的数据，如果成功则直接返回对应的数据，如果失败则返回对应的错误原因
    //返回给前端的状态，如果成功则直接返回success，如果失败返回fail
    private T data;
    //返回给前端：简单的状态描述
    private String message;

    public static <T> CommonResult success(T data) {
        return success(data, "success");
    }

    private static <T> CommonResult success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), data, message);
    }

    public static <T> CommonResult fail(ResultCode code) {
        return new CommonResult<T>(code.getCode(),null,code.getMessage());
    }

}
