package com.uestc.luckyuser.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.uestc.luckyuser.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author jacob
 * @date 2022/4/12 20:07
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
