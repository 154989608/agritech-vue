package com.ruoyi.web.controller.shop;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.shop.MallCategory;
import com.ruoyi.system.service.IMallCategoryService;

@RestController
@RequestMapping("/shop/category")
public class MallCategoryController extends BaseController
{
    @Autowired private IMallCategoryService categoryService;
    @PreAuthorize("@ss.hasPermi('shop:category:list')") @GetMapping("/list")
    public AjaxResult list(MallCategory category) { return success(categoryService.selectCategoryList(category)); }
    @Log(title="商城分类", businessType=BusinessType.EXPORT) @PreAuthorize("@ss.hasPermi('shop:category:export')") @PostMapping("/export")
    public void export(HttpServletResponse response, MallCategory category) { List<MallCategory> list=categoryService.selectCategoryList(category); new ExcelUtil<MallCategory>(MallCategory.class).exportExcel(response, list, "商城分类数据"); }
    @PreAuthorize("@ss.hasPermi('shop:category:query')") @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@PathVariable Long categoryId) { return success(categoryService.selectCategoryById(categoryId)); }
    @PreAuthorize("@ss.hasPermi('shop:category:add')") @Log(title="商城分类", businessType=BusinessType.INSERT) @PostMapping
    public AjaxResult add(@RequestBody MallCategory category) { category.setCreateBy(getUsername()); return toAjax(categoryService.insertCategory(category)); }
    @PreAuthorize("@ss.hasPermi('shop:category:edit')") @Log(title="商城分类", businessType=BusinessType.UPDATE) @PutMapping
    public AjaxResult edit(@RequestBody MallCategory category) { category.setUpdateBy(getUsername()); return toAjax(categoryService.updateCategory(category)); }
    @PreAuthorize("@ss.hasPermi('shop:category:remove')") @Log(title="商城分类", businessType=BusinessType.DELETE) @DeleteMapping("/{categoryId}")
    public AjaxResult remove(@PathVariable Long categoryId) { return toAjax(categoryService.deleteCategoryById(categoryId)); }
}
