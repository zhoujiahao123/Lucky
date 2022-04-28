package com.uestc.luckyuser.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.CommonResult;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.common.Utils;
import com.uestc.luckyuser.dto.request.CategoryCreateReq;
import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @date 2022/4/24 9:24
 */
@Api(tags = "CategoryController")
@RequestMapping("admin/category")
@Controller("adminCategory")
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @RequestMapping("/index")
    @ApiOperation("商品类型首页")
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        IPage<Category> page = categoryService.selectAll(pageNumber);
        ModelAndView modelAndView = new ModelAndView("/admin/category/index.html");
        modelAndView.addObject("data", page);
        modelAndView.addObject("CONTROLLER_NAME", "category");
        modelAndView.addObject("ACTION_NAME", "index");
        return modelAndView;
    }

    @RequestMapping("/createpage")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/category/create.html");
        modelAndView.addObject("CONTROLLER_NAME", "category");
        modelAndView.addObject("ACTION_NAME", "create");
        return modelAndView;
    }

    @ApiOperation("创建类型")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid CategoryCreateReq categoryCreateReq, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()) {
            String errorMessage = Utils.getErrorMessage(bindingResult);
            CommonResult.fail(ResultCode.PARAMETER_VALIDATION_ERROR, errorMessage);
        }
        Category categoryModel = new Category();
        categoryModel.setName(categoryCreateReq.getName());
        categoryModel.setIconUrl(categoryCreateReq.getIconUrl());
        categoryModel.setSort(categoryCreateReq.getSort());

        boolean isCreate = categoryService.isCreate(categoryModel);
        if (isCreate) {
            return "redirect:/admin/category/index";
        }
        throw new BusinessException(ResultCode.CREATE_CATEGORY_FAIL);
    }


}
