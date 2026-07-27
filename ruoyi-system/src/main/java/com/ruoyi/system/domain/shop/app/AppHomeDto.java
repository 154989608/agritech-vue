package com.ruoyi.system.domain.shop.app;

import java.util.List;

public class AppHomeDto { private List<AppBannerDto> banners; private List<AppCategoryDto> categories; private List<AppProductDto> latestProducts; private List<AppProductDto> hotProducts;
    public List<AppBannerDto> getBanners(){return banners;} public void setBanners(List<AppBannerDto> value){banners=value;} public List<AppCategoryDto> getCategories(){return categories;} public void setCategories(List<AppCategoryDto> value){categories=value;} public List<AppProductDto> getLatestProducts(){return latestProducts;} public void setLatestProducts(List<AppProductDto> value){latestProducts=value;} public List<AppProductDto> getHotProducts(){return hotProducts;} public void setHotProducts(List<AppProductDto> value){hotProducts=value;} }
