package com.uestc.luckyuser.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.CommonResult;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.common.Utils;
import com.uestc.luckyuser.dto.request.SellerParam;
import com.uestc.luckyuser.model.Seller;
import com.uestc.luckyuser.service.SellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author jacob
 * @date 2022/4/23 20:11
 */
@Api(tags = "SellerController")
@RequestMapping("/admin/seller")
@Controller
public class SellerController {
    Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Resource
    SellerService sellerService;

    @ApiOperation("运营后台首页，获取商家列表")
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        ModelAndView modelAndView = new ModelAndView();
        logger.info(String.valueOf(pageNumber));
        IPage<Seller> pages = sellerService.listAllSellers(pageNumber);
        modelAndView.addObject("data", pages);
        modelAndView.addObject("CONTROLLER_NAME", "seller");
        modelAndView.addObject("ACTION_NAME", "index");
        return modelAndView;
    }

    @RequestMapping("/createpage")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/seller/create.html");
        modelAndView.addObject("CONTROLLER_NAME", "seller");
        modelAndView.addObject("ACTION_NAME", "create");
        return modelAndView;
    }

    @ApiOperation("创建商家的接口")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid SellerParam sellerParam, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()) {
            String errorMessage = Utils.getErrorMessage(bindingResult);
            CommonResult.fail(ResultCode.PARAMETER_VALIDATION_ERROR, errorMessage);
        }
        Seller seller = new Seller();
        seller.setName(sellerParam.getName());
        boolean isCreate = sellerService.createSellers(seller);
        if (isCreate) {
            return "redirect:/admin/seller/index";
        }
        throw new BusinessException(ResultCode.CREATE_SELLER_FAIL);
    }

    @RequestMapping("down")
    @ResponseBody
    public CommonResult<Boolean> down(@RequestParam("id") Integer id) throws BusinessException {
        boolean result = sellerService.changeSellerStatus(id, 1);
        return CommonResult.success(result);
    }

    @RequestMapping("up")
    @ResponseBody
    public CommonResult<Boolean> up(@RequestParam("id") Integer id) throws BusinessException {
        boolean result = sellerService.changeSellerStatus(id, 0);
        return CommonResult.success(result);
    }
}
