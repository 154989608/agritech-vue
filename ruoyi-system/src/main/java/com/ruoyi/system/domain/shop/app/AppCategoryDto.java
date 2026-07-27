package com.ruoyi.system.domain.shop.app;

import java.util.ArrayList;
import java.util.List;

public class AppCategoryDto {
    private Long categoryId; private Long parentId; private String categoryName; private List<AppCategoryDto> children = new ArrayList<AppCategoryDto>();
    public Long getCategoryId(){return categoryId;} public void setCategoryId(Long value){categoryId=value;}
    public Long getParentId(){return parentId;} public void setParentId(Long value){parentId=value;}
    public String getCategoryName(){return categoryName;} public void setCategoryName(String value){categoryName=value;}
    public List<AppCategoryDto> getChildren(){return children;} public void setChildren(List<AppCategoryDto> value){children=value;}
}
