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

    @ApiOperation("??????id????????????")
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


    @ApiOperation("????????????")
    @RequestMapping("/logup")
    @ResponseBody
    public CommonResult<User> logUp(@RequestBody @Validated UserParam userParam, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            String errorMessage = Utils.getErrorMessage(bindingResult);
            CommonResult.fail(ResultCode.PARAMETER_VALIDATION_ERROR, errorMessage);
        }
        User user = userService.insertUser(userParam);
        return CommonResult.success(user);
    }

    @ApiOperation("????????????")
    @RequestMapping("/login")
    @ResponseBody
    public CommonResult<LoginResponse> login(@Validated @RequestBody LoginParam loginParam,
                                             BindingResult bindingResult, HttpServletRequest request) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            String errorMessage = Utils.getErrorMessage(bindingResult);
            CommonResult.fail(ResultCode.PARAMETER_VALIDATION_ERROR, errorMessage);
        }

        //?????????????????????????????????
        HttpSession session = request.getSession();
        boolean isVerify = userService.verifyCode(loginParam.getCode(), session);
        if (!isVerify) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR);
        }

        //?????????????????????????????????????????????????????????
        LoginResponse response = userService.login(loginParam);

        // ???token??????????????????
        String token = jwtTokenUtil.generateToken(loginParam.getMobilePhoneNumber(), response.getRole());
        response.setToken(token);

        //???id ??? token ??????redis????????????
        //???????????????????????????token?????????
        redisService.set(UserPrefix.getByToken, token, response.getMobilePhoneNumber());
        return CommonResult.success(response);
    }


    /**
     * ??????????????????????????? ???????????? ?????????????????????
     * ????????????????????????????????????id?????????{mibilePhoneNumber}???????????????redis???key
     *
     * @param
     * @param response
     * @throws IOException
     */
    @ApiOperation("?????????????????????")
    @RequestMapping("/getcode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String sessionId = request.getSession().getId();
        Object[] objs = VerifyUtil.newBuilder()
                .setWidth(120)   //?????????????????????
                .setHeight(35)   //?????????????????????
                .setSize(5)      //?????????????????????
                .setLines(5)    //????????????????????????
                .setFontSize(35) //?????????????????????
                .setTilt(true)   //????????????????????????
                .setBackgroundColor(Color.LIGHT_GRAY) //??????????????????????????????
                .build()         //??????VerifyUtil??????
                .createImage();  //????????????
        String verifyCode = (String) objs[0];
        BufferedImage image = (BufferedImage) objs[1];
        loggerFactory.info(verifyCode);

        //??????redis????????????????????????
        redisService.set(UserPrefix.VerifyTimes, sessionId, 5);
        //??????????????????session???
        request.getSession().setAttribute(VERIFY_CODE_SESSION, verifyCode);

        //?????????????????????
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
    }

    @ApiOperation(value = "??????????????????")
    @RequestMapping("/info")
    @ResponseBody
    public CommonResult<UserInfoResponse> getUserInfoResponse() {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        User user = userService.findUserByToken();
        BeanUtils.copyProperties(user, userInfoResponse);
        return CommonResult.success(userInfoResponse);
    }
}
