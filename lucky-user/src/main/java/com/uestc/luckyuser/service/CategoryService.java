package com.uestc.luckyuser.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uestc.luckyuser.model.Category;

import java.util.List;

/**
 * @author jacob
 * @date 2022/4/24 9:26
 */
public interface CategoryService {
    IPage<Category> selectAll(int pageNumber);

    List<Category> selectAll();

    boolean isCreate(Category category);

    Category selectCategoryById(long id);
}

