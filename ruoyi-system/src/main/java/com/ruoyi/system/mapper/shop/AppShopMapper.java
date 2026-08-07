package com.ruoyi.system.mapper.shop;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.shop.app.AppBannerDto;
import com.ruoyi.system.domain.shop.app.AppCartPreviewItemDto;
import com.ruoyi.system.domain.shop.app.AppCategoryDto;
import com.ruoyi.system.domain.shop.app.AppProductDto;
import com.ruoyi.system.domain.shop.app.AppProductSkuDto;

public interface AppShopMapper {
    List<AppBannerDto> selectActiveBanners();
    List<AppCategoryDto> selectEnabledCategories();
    List<AppProductDto> selectLatestProducts();
    List<AppProductDto> selectHotProducts();
    List<AppProductDto> selectPublishedProducts(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);
    AppProductDto selectPublishedProductById(Long productId);
    List<AppProductSkuDto> selectPublishedSkusByProductId(Long productId);
    List<AppCartPreviewItemDto> selectCartPreviewItems(@Param("skuIds") List<Long> skuIds);
}
