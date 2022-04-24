package com.uestc.luckyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uestc.luckyuser.dao.CategoryMapper;
import com.uestc.luckyuser.model.Category;
import com.uestc.luckyuser.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jacob
 * @date 2022/4/24 9:30
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public IPage<Category> selectAll(int pageNumber) {
        Page<Category> page = new Page<>(pageNumber, 1);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        IPage<Category> iPage = categoryMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public List<Category> selectAll() {
        List<Category> categories = categoryMapper.selectList(new QueryWrapper<>());
        return categories;
    }

    @Override
    public boolean isCreate(Category category) {
        int count = categoryMapper.insert(category);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Category selectCategoryById(long id) {
        Category category = categoryMapper.selectById(id);
        return category;
    }
}
