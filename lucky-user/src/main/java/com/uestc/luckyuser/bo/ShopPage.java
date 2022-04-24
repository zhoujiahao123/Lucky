package com.uestc.luckyuser.bo;

import com.uestc.luckyuser.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jacob
 * @date 2022/4/24 16:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopPage {
    private List<ShopBo> records;
    private long total;
    private long size;
    private long pages;
    private long current;
}
