package com.uestc.luckyuser.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jacob
 * @date 2022/4/19 14:30
 */
@Data
public class UserParam {
    @NotBlank(message = "昵称不能为空")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotNull(message = "性别不能为空")
    private Integer gender;
    @NotBlank(message = "手机号码不能为空")
    private String mobilePhoneNumber;
}
