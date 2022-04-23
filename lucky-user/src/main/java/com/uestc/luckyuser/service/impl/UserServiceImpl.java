package com.uestc.luckyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.JwtTokenUtil;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.controller.UserController;
import com.uestc.luckyuser.dao.UserMapper;
import com.uestc.luckyuser.dto.request.LoginParam;
import com.uestc.luckyuser.dto.request.UserParam;
import com.uestc.luckyuser.dto.response.LoginResponse;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.redis.RedisService;
import com.uestc.luckyuser.redis.UserPrefix;
import com.uestc.luckyuser.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @Resource
    private JwtTokenUtil jwtTokenUtil;


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
    public boolean verifyCode(String code, HttpSession session) {
        if (code == null || code.isEmpty()) {
            return false;
        }
        long verifyTimes = redisService.decr(UserPrefix.VerifyTimes, session.getId());
        if (verifyTimes >= 0) {
            String realCode = (String) session.getAttribute(UserController.VERIFY_CODE_SESSION);
            if (code.toUpperCase().equals(realCode.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUserByMobilePhoneNumber(String mobilePhoneNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile_phone_number", mobilePhoneNumber);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User findUserByToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        String mobilePhoneNumber = jwtTokenUtil.getUsernameFromToken(token);
        return findUserByMobilePhoneNumber(mobilePhoneNumber);
    }

    @Override
    public int countUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(queryWrapper);
        return users.size();
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
