package com.uestc.luckyuser.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jacob
 * @date 2022/4/24 9:54
 */
@Data
public class CategoryCreateReq {
    @NotBlank(message = "类型名称不能为空")
    private String name;
    @NotBlank(message = "iconurl不能为空")
    private String iconUrl;
    @NotNull(message = "权重不能为空")
    private int sort;
}
