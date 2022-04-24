package com.uestc.luckyuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jacob
 * @date 2022/4/24 14:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private BigDecimal remarkScore;
    private int pricePerMan;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int categoryId;
    private String tags;
    private String startTime;
    private String endTime;
    private String address;
    private int sellerId;
    private String iconUrl;
}
