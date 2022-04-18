package com.uestc.luckyuser.service;

import com.uestc.luckyuser.model.User;

/**
 * @author jacob
 * @date 2022/4/18 9:49
 */

public interface UserService {

    User getUserById(Long id);
}
