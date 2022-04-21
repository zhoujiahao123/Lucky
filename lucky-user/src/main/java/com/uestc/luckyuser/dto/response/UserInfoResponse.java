package com.uestc.luckyuser.dto.response;

import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/21 10:41
 */
@Data
public class UserInfoResponse {
    private String name;
    private Integer gender;
    private String mobilePhoneNumber;
    private String token;
}
