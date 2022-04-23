package com.uestc.luckyuser.controller;

import com.uestc.luckyuser.common.*;

import com.uestc.luckyuser.dto.request.LoginParam;
import com.uestc.luckyuser.dto.request.UserParam;
import com.uestc.luckyuser.dto.response.LoginResponse;
import com.uestc.luckyuser.dto.response.UserInfoResponse;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.redis.RedisService;
import com.uestc.luckyuser.redis.UserPrefix;
import com.uestc.luckyuser.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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

    Logger loggerFactory = LoggerFactory.getLogger(UserController.class);
    public static final String VERIFY_CODE_SESSION = "VERIFY_CODE_SESSION";

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
    @PreAuthorize("hasAnyRole('ROLE_NORMAL')")
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
                                             BindingResult bindingResult,HttpServletRequest request) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            Utils.getErrorMessage(bindingResult);
        }

        //首先验证验证码是否正确
        HttpSession session = request.getSession();
        boolean isVerify = userService.verifyCode(loginParam.getCode(), session);
        if (!isVerify) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR);
        }

        //验证码正确，则开始验证登录信息是否正确
        LoginResponse response = userService.login(loginParam);

        // 把token上传到服务器
        String token = jwtTokenUtil.generateToken(loginParam.getMobilePhoneNumber(), response.getRole());
        response.setToken(token);

        //把id 和 token 通过redis关联起来
        //主要是为了能够控制token的有效
        redisService.set(UserPrefix.getByToken, token, response.getMobilePhoneNumber());
        return CommonResult.success(response);
    }


    /**
     * 无论是手机验证登录 亦或者是 图形验证码登录
     * 都希望用户能提供一个唯一id，类似{mibilePhoneNumber}，以便作为redis的key
     *
     * @param
     * @param response
     * @throws IOException
     */
    @ApiOperation("获取验证码操作")
    @RequestMapping("/getcode")
    public void getCode(HttpServletResponse response,HttpServletRequest request) throws IOException {
        String sessionId = request.getSession().getId();
        Object[] objs = VerifyUtil.newBuilder()
                .setWidth(120)   //设置图片的宽度
                .setHeight(35)   //设置图片的高度
                .setSize(5)      //设置字符的个数
                .setLines(5)    //设置干扰线的条数
                .setFontSize(35) //设置字体的大小
                .setTilt(true)   //设置是否需要倾斜
                .setBackgroundColor(Color.LIGHT_GRAY) //设置验证码的背景颜色
                .build()         //构建VerifyUtil项目
                .createImage();  //生成图片
        String verifyCode = (String) objs[0];
        BufferedImage image = (BufferedImage) objs[1];
        loggerFactory.info(verifyCode);

        //放入redis中，实现验证功能
        redisService.set(UserPrefix.VerifyTimes, sessionId, 5);
        //将验证码放入session中
        request.getSession().setAttribute(VERIFY_CODE_SESSION,verifyCode);

        //返回图片给前端
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping("/info")
    @ResponseBody
    public CommonResult<UserInfoResponse> getUserInfoResponse() {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        User user = userService.findUserByToken();
        BeanUtils.copyProperties(user, userInfoResponse);
        return CommonResult.success(userInfoResponse);
    }
}
