package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.shop.MallCategory;
import com.ruoyi.system.mapper.shop.MallCategoryMapper;
import com.ruoyi.system.service.IMallCategoryService;

@Service
public class MallCategoryServiceImpl implements IMallCategoryService
{
    @Autowired
    private MallCategoryMapper categoryMapper;

    @Override
    public List<MallCategory> selectCategoryList(MallCategory category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    @Override
    public MallCategory selectCategoryById(Long categoryId)
    {
        return categoryMapper.selectCategoryById(categoryId);
    }

    @Override
    public int insertCategory(MallCategory category)
    {
        validateParent(category);
        category.setCreateTime(null);
        return categoryMapper.insertCategory(category);
    }

    @Override
    public int updateCategory(MallCategory category)
    {
        MallCategory existing = requireCategory(category.getCategoryId());
        if (!StringUtils.equals(existing.getStatus(), category.getStatus()) && "1".equals(category.getStatus())
                && categoryMapper.countProducts(existing.getCategoryId()) > 0)
        {
            throw new ServiceException("分类下存在商品，不允许停用");
        }
        validateParent(category);
        category.setUpdateTime(null);
        return categoryMapper.updateCategory(category);
    }

    @Override
    public int deleteCategoryById(Long categoryId)
    {
        requireCategory(categoryId);
        if (categoryMapper.countChildren(categoryId) > 0)
        {
            throw new ServiceException("分类存在子分类，不允许删除");
        }
        if (categoryMapper.countProducts(categoryId) > 0)
        {
            throw new ServiceException("分类下存在商品，不允许删除");
        }
        return categoryMapper.deleteCategoryById(categoryId);
    }

    private void validateParent(MallCategory category)
    {
        if (category.getLevel() == null || (category.getLevel() != 1 && category.getLevel() != 2))
        {
            throw new ServiceException("分类仅支持一级或二级");
        }
        if (category.getLevel() == 1)
        {
            if (category.getParentId() != null)
            {
                throw new ServiceException("一级分类不能设置上级分类");
            }
            return;
        }
        if (category.getParentId() == null || category.getParentId().equals(category.getCategoryId()))
        {
            throw new ServiceException("二级分类必须选择有效的一级分类");
        }
        MallCategory parent = requireCategory(category.getParentId());
        if (parent.getLevel() != 1 || !"0".equals(parent.getStatus()))
        {
            throw new ServiceException("上级分类必须为启用的一级分类");
        }
    }

    private MallCategory requireCategory(Long categoryId)
    {
        MallCategory category = categoryMapper.selectCategoryById(categoryId);
        if (category == null)
        {
            throw new ServiceException("分类不存在或已删除");
        }
        return category;
    }
}
