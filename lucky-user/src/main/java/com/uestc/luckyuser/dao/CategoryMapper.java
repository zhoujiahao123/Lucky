package com.uestc.luckyuser.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uestc.luckyuser.model.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jacob
 * @date 2022/4/24 9:31
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
