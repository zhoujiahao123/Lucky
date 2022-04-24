package com.uestc.luckyuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.model.Seller;

import java.util.List;

/**
 * @author jacob
 * @date 2022/4/23 20:45
 */
public interface SellerService {

    IPage<Seller> listAllSellers(int pageNumber);

    boolean createSellers(Seller seller);

    boolean changeSellerStatus(int id,int disabledFlag) throws BusinessException;
}
