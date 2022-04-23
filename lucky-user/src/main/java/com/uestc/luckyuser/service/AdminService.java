package com.uestc.luckyuser.service;

import org.springframework.stereotype.Service;

/**
 * @author jacob
 * @date 2022/4/23 10:48
 */
public interface AdminService {
    boolean isLoginSuccess(String username, String password);
}
