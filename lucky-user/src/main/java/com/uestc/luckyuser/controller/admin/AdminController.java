package com.uestc.luckyuser.controller.admin;

import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.service.AdminService;
import com.uestc.luckyuser.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jacob
 * @date 2022/4/23 9:53
 */
@Controller
@RequestMapping("admin/admin")
public class AdminController {

    @Resource
    UserService userService;
    @Resource
    AdminService adminService;

    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;

    @Resource
    HttpServletRequest request;
    private static final String CURRENT_ADMIN_SESSIONS = "CURRENT_ADMIN_SESSIONS";


    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/admin/admin/index");

        mv.addObject("userCount", userService.countUser());
        mv.addObject("CONTROLLER_NAME", "admin");
        mv.addObject("ACTION_NAME", "index");

        return mv;
    }

    @RequestMapping("/loginpage")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView("/admin/admin/login");
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name = "email") String email,
                        @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new BusinessException(ResultCode.USER_OR_PASSWORD_EMPTY);
        }

        //登录操作
        if (adminService.isLoginSuccess(email,encodeByMd5(password))) {
            request.getSession().setAttribute(CURRENT_ADMIN_SESSIONS, email);
            return "redirect:/admin/admin/index";
        }

        throw new BusinessException(ResultCode.PHONE_NOT_MEET_PASSWORD);
    }

    private String encodeByMd5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encode = new BASE64Encoder();
        return base64Encode.encode(md.digest(password.getBytes("utf-8")));
    }
}
