package com.uestc.luckyuser.dto.request;

import lombok.Data;

/**
 * @author jacob
 * @date 2022/4/23 20:40
 */
@Data
public class PageParam {
    private int pageNumber = 1;
    private int pageSize = 20;
}
