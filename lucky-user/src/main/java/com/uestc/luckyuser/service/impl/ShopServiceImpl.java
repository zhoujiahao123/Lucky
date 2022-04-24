package com.uestc.luckyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uestc.luckyuser.bo.ShopBo;
import com.uestc.luckyuser.bo.ShopPage;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.dao.ShopMapper;
import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.model.Seller;
import com.uestc.luckyuser.model.Shop;
import com.uestc.luckyuser.service.CategoryService;
import com.uestc.luckyuser.service.SellerService;
import com.uestc.luckyuser.service.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jacob
 * @date 2022/4/24 14:44
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    ShopMapper shopMapper;
    @Resource
    SellerService sellerService;
    @Resource
    CategoryService categoryService;

    @Override
    public ShopPage selectAll(int pageNumber) {
        Page<Shop> page = new Page<>(pageNumber, 1);
        IPage<Shop> iPages = shopMapper.selectPage(page, new QueryWrapper<>());

        //直接利用mybatisplus不满足需求，重新封装一个类，返回给前端
        List<Shop> shops = iPages.getRecords();
        List<ShopBo> shopBos = new ArrayList<>();
        for (Shop shop : shops) {
            ShopBo shopBo = new ShopBo();
            BeanUtils.copyProperties(shop, shopBo);
            shopBo.setCategory(categoryService.selectCategoryById(shop.getCategoryId()));
            shopBo.setSeller(sellerService.selectById(shop.getSellerId()));
            shopBos.add(shopBo);
        }
        ShopPage shopPage = new ShopPage();
        shopPage.setRecords(shopBos);
        shopPage.setCurrent(iPages.getCurrent());
        shopPage.setPages(iPages.getPages());
        shopPage.setTotal(iPages.getTotal());
        return shopPage;
    }

    @Override
    public boolean createShop(Shop shop) throws BusinessException {
        //检查商家是否存在
        Seller seller = sellerService.selectById(shop.getSellerId());
        if (seller == null) {
            throw new BusinessException(ResultCode.SELLER_NOTFOUND);
        }
        if (seller.getDisabledFlag() == 1) {
            throw new BusinessException(ResultCode.SELLER_ALREADY_DISABLE);
        }

        //检查类目是否存在
        Category category = categoryService.selectCategoryById(shop.getCategoryId());
        if (category == null) {
            throw new BusinessException(ResultCode.CATEGORY_NOTFOUND);
        }

        int count = shopMapper.insert(shop);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ShopBo> recommend(BigDecimal longitude, BigDecimal latitude) {
        List<ShopBo> recommend = shopMapper.recommend(longitude, latitude);
        return recommend;
    }

    @Override
    public List<ShopBo> search(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderby, Integer categoryId, String tags) {
        List<ShopBo> shopBoList = shopMapper.search(longitude, latitude, keyword, orderby, categoryId, tags);
        shopBoList.forEach(shopBo -> {
            shopBo.setSeller(sellerService.selectById(shopBo.getSellerId()));
            shopBo.setCategory(categoryService.selectCategoryById(shopBo.getCategoryId()));
        });
        return shopBoList;
    }

    @Override
    public List<Map<String, Object>> searchGroupByTags(String keyword, Integer categoryId, String tags) {
        return shopMapper.searchGroupByTags(keyword, categoryId, tags);
    }
}
