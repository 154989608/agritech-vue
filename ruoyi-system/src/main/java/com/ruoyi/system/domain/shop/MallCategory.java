package com.ruoyi.system.domain.shop;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class MallCategory extends BaseEntity
{
    @Excel(name = "分类ID", cellType = Excel.ColumnType.NUMERIC)
    private Long categoryId;
    @Excel(name = "父分类ID", cellType = Excel.ColumnType.NUMERIC)
    private Long parentId;
    @NotBlank(message = "分类名称不能为空")
    @Excel(name = "分类名称")
    private String categoryName;
    @NotNull(message = "分类层级不能为空")
    @Excel(name = "层级", readConverterExp = "1=一级,2=二级")
    private Integer level;
    @Excel(name = "排序", cellType = Excel.ColumnType.NUMERIC)
    private Integer sortNum;
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public Integer getSortNum() { return sortNum; }
    public void setSortNum(Integer sortNum) { this.sortNum = sortNum; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
