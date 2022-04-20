package com.uestc.luckyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.dao.UserMapper;
import com.uestc.luckyuser.dto.request.LoginParam;
import com.uestc.luckyuser.dto.request.UserParam;
import com.uestc.luckyuser.dto.response.LoginResponse;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.redis.RedisService;
import com.uestc.luckyuser.redis.UserPrefix;
import com.uestc.luckyuser.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jacob
 * @date 2022/4/18 9:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RedisService redisService;

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional
    public User insertUser(UserParam userParam) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        String passwordMd5 = encodeByMd5(userParam.getPassword());
        user.setPassword(passwordMd5);
        int count = userMapper.insert(user);
        if (count == 0) {
            throw new BusinessException(ResultCode.USER_LOG_UP_FAILED);
        }
        return getUserById(user.getId());
    }

    @Override
    public LoginResponse login(LoginParam loginParam) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile_phone_number", loginParam.getMobilePhoneNumber())
                .eq("password", encodeByMd5(loginParam.getPassword()));
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ResultCode.PHONE_NOT_MEET_PASSWORD);
        }
        LoginResponse response = new LoginResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    /**
     * 如果{code} 失败次数已经达到5次，那么返回{false}
     * 如果{code} 验证失败，那么返回{false}
     * 否则，返回{true}
     *
     * @param code
     * @return
     */
    @Override
    public boolean verifyCode(String code, String mobilePhoneNumber) {
        if (code == null || code.isEmpty()) {
            return false;
        }
        long verifyTimes = redisService.decr(UserPrefix.VerifyTimes, mobilePhoneNumber);
        if (verifyTimes >= 0) {
            String realCode = redisService.get(UserPrefix.VerifyCode, mobilePhoneNumber, String.class);
            if (code.toUpperCase().equals(realCode.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param password 明文密码
     * @return 经过Md5加密的密码
     */
    private String encodeByMd5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(messageDigest.digest(password.getBytes("utf-8")));
    }
}
