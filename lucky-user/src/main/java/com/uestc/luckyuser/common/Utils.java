package com.uestc.luckyuser.common;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author jacob
 * @date 2022/4/19 15:42
 */
public class Utils {

    public static void getErrorMessage(BindingResult bindingResult) throws BusinessException {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (fieldError.getDefaultMessage().equals("昵称不能为空")) {
                throw new BusinessException(ResultCode.NAME_CANNOT_BLANK);
            } else if (fieldError.getDefaultMessage().equals("密码不能为空")) {
                throw new BusinessException(ResultCode.PASSWORD_CANNOT_BLANK);
            } else if (fieldError.getDefaultMessage().equals("性别不能为空")) {
                throw new BusinessException(ResultCode.GENDER_CANNOT_BLANK);
            } else if (fieldError.getDefaultMessage().equals("手机号码不能为空")) {
                throw new BusinessException(ResultCode.PHONE_CANNOT_BLANK);
            }
        }
    }
}
