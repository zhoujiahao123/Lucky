package com.uestc.luckyuser.controller;

import com.uestc.luckyuser.common.*;

import com.uestc.luckyuser.dto.request.LoginParam;
import com.uestc.luckyuser.dto.request.UserParam;
import com.uestc.luckyuser.dto.response.LoginResponse;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.redis.RedisService;
import com.uestc.luckyuser.redis.UserPrefix;
import com.uestc.luckyuser.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
 * @author jacob
 * @date 2022/4/18 9:49
 */
@Api(tags = "LuckyUserController")
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    JwtTokenUtil jwtTokenUtil;
    @Resource
    RedisService redisService;

    @RequestMapping("/index")
    public ModelAndView indexTest() {
        String username = "jacob";
        ModelAndView mav = new ModelAndView("index.html");
        mav.addObject("name", username);
        return mav;
    }

    @ApiOperation("根据id获取用户")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<User> getUser(@PathVariable(name = "id") Long id) throws BusinessException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOTFOUND);
        } else {
            return CommonResult.success(user);
        }
    }


    @ApiOperation("用户注册")
    @RequestMapping("/logup")
    @ResponseBody
    public CommonResult<User> logUp(@RequestBody @Validated UserParam userParam, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            Utils.getErrorMessage(bindingResult);
        }
        User user = userService.insertUser(userParam);
        return CommonResult.success(user);
    }

    @ApiOperation("用户登录")
    @RequestMapping("/login")
    @ResponseBody
    public CommonResult<LoginResponse> login(@Validated @RequestBody LoginParam loginParam,
                                             BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            Utils.getErrorMessage(bindingResult);
        }
        LoginResponse response = userService.login(loginParam);
        //为该用户生成一个Token，然后返回给客户端，客户端每次通过URL重写
        // 把token上传到服务器
        String token = jwtTokenUtil.generateToken(loginParam.getMobilePhoneNumber(), "123456");
        response.setToken(token);
        //把id 和 token 通过redis关联起来
        //主要是为了能够控制token的有效
        redisService.set(UserPrefix.getByToken, token, response.getMobilePhoneNumber());
        return CommonResult.success(response);
    }
}
