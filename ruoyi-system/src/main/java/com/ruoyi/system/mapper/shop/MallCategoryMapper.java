package com.ruoyi.system.mapper.shop;

import java.util.List;
import com.ruoyi.system.domain.shop.MallCategory;

public interface MallCategoryMapper
{
    List<MallCategory> selectCategoryList(MallCategory category);
    MallCategory selectCategoryById(Long categoryId);
    int insertCategory(MallCategory category);
    int updateCategory(MallCategory category);
    int deleteCategoryById(Long categoryId);
    int countChildren(Long categoryId);
    int countProducts(Long categoryId);
}
