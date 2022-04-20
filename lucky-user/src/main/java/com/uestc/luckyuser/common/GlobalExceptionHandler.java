package com.uestc.luckyuser.common;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jacob
 * @date 2022/4/18 14:19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult doError(Exception ex) throws Exception {
        //该异常用于处理
        if (ex instanceof BusinessException) {
            BusinessException e = (BusinessException) ex;
            return CommonResult.fail(e.getResultCode());
        } else if (ex instanceof DuplicateKeyException) {
            return CommonResult.fail(ResultCode.MOBILE_PHONE_NUMBER_EXIST);
        } else {
            ex.printStackTrace();
            throw ex;
        }
    }
}
