package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.shop.app.AppCartPreviewDto;
import com.ruoyi.system.domain.shop.app.AppCartPreviewRequest;
import com.ruoyi.system.domain.shop.app.AppCategoryDto;
import com.ruoyi.system.domain.shop.app.AppHomeDto;
import com.ruoyi.system.domain.shop.app.AppProductDto;

public interface IAppShopService { AppHomeDto getHome(); List<AppCategoryDto> getCategories(); List<AppProductDto> getProducts(Long categoryId, String keyword); AppProductDto getProduct(Long productId); AppCartPreviewDto previewCart(AppCartPreviewRequest request); }
