package com.uestc.luckyuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.luckyuser.bo.ShopBo;
import com.uestc.luckyuser.bo.ShopPage;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.model.Shop;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jacob
 * @date 2022/4/24 14:34
 */

public interface ShopService {
    ShopPage selectAll(int pageNumber);

    boolean createShop(Shop shop) throws BusinessException;


    List<ShopBo> recommend(BigDecimal longitude,BigDecimal latitude);

    List<ShopBo> search(BigDecimal longitude,
                        BigDecimal latitude, String keyword,Integer orderby,
                        Integer categoryId,String tags);

    Map<String,Object> searchEs(BigDecimal longitude,BigDecimal latitude,
                                String keyword,Integer orderby,Integer categoryId,String tags) throws IOException;

    List<Map<String,Object>> searchGroupByTags(String keyword,
                                               Integer categoryId,String tags);
}
