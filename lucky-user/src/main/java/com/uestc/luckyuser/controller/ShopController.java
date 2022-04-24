package com.uestc.luckyuser.controller;

import com.uestc.luckyuser.bo.ShopBo;
import com.uestc.luckyuser.common.BusinessException;
import com.uestc.luckyuser.common.CommonResult;
import com.uestc.luckyuser.common.ResultCode;
import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.service.CategoryService;
import com.uestc.luckyuser.service.ShopService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacob
 * @date 2022/4/24 16:16
 */
@Controller("shopController")
@RequestMapping("/shop")
public class ShopController {

    @Resource
    ShopService shopService;
    @Resource
    CategoryService categoryService;

    @RequestMapping("/recommend")
    @ResponseBody
    public CommonResult<List<ShopBo>> recommend(@RequestParam(name = "longitude") BigDecimal longitude,
                                                @RequestParam(name = "latitude") BigDecimal latitude) throws BusinessException {
        if (longitude == null || latitude == null) {
            throw new BusinessException(ResultCode.LONGITUDE_OR_LATITUDE_CANNOT_EMPTY);
        }
        List<ShopBo> shopBoList = shopService.recommend(longitude, latitude);
        return CommonResult.success(shopBoList);
    }

    @RequestMapping("/search")
    @ResponseBody
    public CommonResult<Map<String, Object>> search(@RequestParam(name = "longitude") BigDecimal longitude,
                                                    @RequestParam(name = "latitude") BigDecimal latitude,
                                                    @RequestParam(name = "keyword") String keyword,
                                                    @RequestParam(name = "orderby", required = false) Integer orderby,
                                                    @RequestParam(name = "categoryId", required = false) Integer categoryId,
                                                    @RequestParam(name = "tags", required = false) String tags) throws BusinessException {
        if (StringUtils.isEmpty(keyword) || longitude == null || latitude == null) {
            throw new BusinessException(ResultCode.PARAMETER_VALIDATION_ERROR);
        }
        List<ShopBo> shopModelList = shopService.search(longitude,latitude,keyword,orderby,categoryId,tags);
        List<Category> categoryModelList = categoryService.selectAll();
        List<Map<String,Object>> tagsAggregation = shopService.searchGroupByTags(keyword,categoryId,tags);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("shop",shopModelList);
        resMap.put("category",categoryModelList);
        resMap.put("tags",tagsAggregation);
        return CommonResult.success(resMap);
    }
}
