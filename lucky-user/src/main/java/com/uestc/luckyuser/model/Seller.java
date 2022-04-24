package com.uestc.luckyuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jacob
 * @date 2022/4/23 20:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    private long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private BigDecimal remarkScore;
    private int disabledFlag;
}
