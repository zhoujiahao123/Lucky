package com.uestc.luckyuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jacob
 * @date 2022/4/24 9:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private String iconUrl;
    private Integer sort;
}
