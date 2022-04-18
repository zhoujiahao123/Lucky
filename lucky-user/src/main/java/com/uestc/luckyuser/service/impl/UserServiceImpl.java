package com.uestc.luckyuser.service.impl;

import com.uestc.luckyuser.dao.UserMapper;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jacob
 * @date 2022/4/18 9:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
}
