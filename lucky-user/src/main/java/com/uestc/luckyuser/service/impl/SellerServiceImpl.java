package com.uestc.luckyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.dao.SellerMapper;
import com.uestc.luckyuser.model.Seller;
import com.uestc.luckyuser.service.SellerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jacob
 * @date 2022/4/23 20:51
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Resource
    SellerMapper sellerMapper;

    @Override
    public IPage<Seller> listAllSellers(int pageNumber) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<Seller> page = new Page<>(pageNumber, 1);
        IPage<Seller> pages = sellerMapper.selectPage(page, queryWrapper);
        return pages;
    }

    @Override
    public boolean createSellers(Seller seller) {
        int count = sellerMapper.insert(seller);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changeSellerStatus(int id, int disabledFlag) throws BusinessException {
        Seller seller = sellerMapper.selectById(id);
        if (seller == null) {
            throw new BusinessException(ResultCode.SELLER_NOTFOUND);
        }
        seller.setDisabledFlag(disabledFlag);
        int count = sellerMapper.updateById(seller);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Seller selectById(long sellerId) {
        Seller seller = sellerMapper.selectById(sellerId);
        return seller;
    }
}
