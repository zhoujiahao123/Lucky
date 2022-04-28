package com.uestc.luckyuser.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.luckyuser.bo.ShopBo;
import com.uestc.luckyuser.bo.ShopPage;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.CommonResult;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.common.Utils;
import com.uestc.luckyuser.dto.request.ShopCreateReq;
import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.model.Shop;
import com.uestc.luckyuser.service.ShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author jacob
 * @date 2022/4/24 14:33
 */
@Controller("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

    @Resource
    ShopService shopService;

    @RequestMapping("/index")
    @ApiOperation("商品类型首页")
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        ShopPage page = shopService.selectAll(pageNumber);
        ModelAndView modelAndView = new ModelAndView("/admin/shop/index.html");
        modelAndView.addObject("data", page);
        modelAndView.addObject("CONTROLLER_NAME", "shop");
        modelAndView.addObject("ACTION_NAME", "index");
        return modelAndView;
    }

    @RequestMapping("/createpage")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/shop/create.html");
        modelAndView.addObject("CONTROLLER_NAME", "shop");
        modelAndView.addObject("ACTION_NAME", "create");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid ShopCreateReq shopCreateReq, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()) {
            String errorMessage = Utils.getErrorMessage(bindingResult);
            CommonResult.fail(ResultCode.PARAMETER_VALIDATION_ERROR, errorMessage);
        }
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopCreateReq, shop);
        boolean isCreate = shopService.createShop(shop);
        if (isCreate) {
            return "redirect:/admin/shop/index";
        }
        throw new BusinessException(ResultCode.CREATE_SHOP_FAIL);
    }
}
