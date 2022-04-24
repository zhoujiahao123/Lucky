package com.uestc.luckyuser.controller;

import com.uestc.luckyuser.common.CommonResult;
import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jacob
 * @date 2022/4/24 10:15
 */
@Controller("category")
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @RequestMapping("/list")
    @ResponseBody
    public CommonResult<List<Category>> list() {
        List<Category> list = categoryService.selectAll();
        return CommonResult.success(list);
    }
}
