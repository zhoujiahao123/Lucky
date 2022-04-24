package com.uestc.luckyuser.bo;

import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.model.Seller;
import com.uestc.luckyuser.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jacob
 * @date 2022/4/24 15:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopBo {
    private long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private BigDecimal remarkScore;
    private int pricePerMan;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int categoryId;
    private Category category;
    private String tags;
    private String startTime;
    private String endTime;
    private String address;
    private int sellerId;
    private Seller seller;
    private String iconUrl;
    private Integer distance;
}
