package com.uestc.luckyuser.controller;

import com.uestc.luckyuser.common.CommonResult;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.common.UserException;
import com.uestc.luckyuser.model.User;
import com.uestc.luckyuser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;


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

    @ApiOperation("根据id获取用户")
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<User> getUser(@PathVariable(name = "id") Long id) throws UserException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserException(ResultCode.USER_NOTFOUND);
        } else {
            return CommonResult.success(user);
        }

    }
}
