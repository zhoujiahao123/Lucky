package com.uestc.luckyuser.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author jacob
 * @date 2022/4/23 21:44
 */
@Data
public class SellerParam {
    @NotBlank(message = "商家名称不能为空")
    private String name;
}
