package com.uestc.luckyuser.service;


import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.dto.request.LoginParam;
import com.uestc.luckyuser.dto.request.UserParam;
import com.uestc.luckyuser.dto.response.LoginResponse;
import com.uestc.luckyuser.model.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author jacob
 * @date 2022/4/18 9:49
 */

public interface UserService {

    User getUserById(Long id);

    User insertUser(UserParam userParam) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;

    LoginResponse login(LoginParam loginParam) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException;

    boolean verifyCode(String code,String mobilePhoneNumber);

    User findUserByMobilePhoneNumber(String mobilePhoneNumber);
}
