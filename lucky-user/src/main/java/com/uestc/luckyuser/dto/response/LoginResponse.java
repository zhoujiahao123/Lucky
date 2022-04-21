package com.uestc.luckyuser.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jacob
 * @date 2022/4/20 10:24
 */
@Data
public class LoginResponse {
    private String name;
    private Integer gender;
    private String mobilePhoneNumber;
    private String token;
    private String role;
}
