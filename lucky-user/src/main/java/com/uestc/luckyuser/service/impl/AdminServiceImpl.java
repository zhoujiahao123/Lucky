package com.uestc.luckyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uestc.luckyuser.dao.AdminMapper;
import com.uestc.luckyuser.model.Admin;
import com.uestc.luckyuser.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jacob
 * @date 2022/4/23 10:50
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    @Override
    public boolean isLoginSuccess(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username).
                eq("password", password);
        Admin admin = adminMapper.selectOne(wrapper);
        if (admin == null) {
            return false;
        }
        return true;
    }

}
