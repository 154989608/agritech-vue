package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.shop.MallCategory;

public interface IMallCategoryService
{
    List<MallCategory> selectCategoryList(MallCategory category);
    MallCategory selectCategoryById(Long categoryId);
    int insertCategory(MallCategory category);
    int updateCategory(MallCategory category);
    int deleteCategoryById(Long categoryId);
}
