package com.uestc.luckyuser.common;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author jacob
 * @date 2022/4/19 15:42
 */
public class Utils {

    public static String getErrorMessage(BindingResult bindingResult) throws BusinessException {
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage());
            sb.append(",");
        }
        String res = sb.substring(0, sb.length() - 1);
        return res;
    }
}
