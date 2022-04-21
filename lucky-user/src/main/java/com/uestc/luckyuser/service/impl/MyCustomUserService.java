package com.uestc.luckyuser.service.impl;

import com.uestc.luckyuser.bo.AdminUserDetails;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jacob
 * @date 2022/4/21 15:01
 */
@Component
public class MyCustomUserService implements UserDetailsService {
    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByMobilePhoneNumber(s);
        AdminUserDetails details = new AdminUserDetails(user);
        return details;
    }
}
