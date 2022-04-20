package com.uestc.luckyuser.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author jacob
 * @date 2022/4/19 16:05
 */
@Data
public class LoginParam {
    @NotBlank(message = "手机号码不能为空")
    private String mobilePhoneNumber;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "验证码不能为空")
    private String code;
}
