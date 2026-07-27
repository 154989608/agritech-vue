package com.ruoyi.system.domain.shop.app;

import java.util.List;

public class AppCartPreviewDto { private List<AppCartPreviewItemDto> items; private Long productAmountCent;
    public List<AppCartPreviewItemDto> getItems(){return items;} public void setItems(List<AppCartPreviewItemDto> value){items=value;} public Long getProductAmountCent(){return productAmountCent;} public void setProductAmountCent(Long value){productAmountCent=value;} }
