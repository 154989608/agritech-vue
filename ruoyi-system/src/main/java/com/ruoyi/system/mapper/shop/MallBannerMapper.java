package com.ruoyi.system.mapper.shop;
import java.util.List;
import com.ruoyi.system.domain.shop.MallBanner;
public interface MallBannerMapper { List<MallBanner> selectBannerList(MallBanner banner); MallBanner selectBannerById(Long id); int insertBanner(MallBanner banner); int updateBanner(MallBanner banner); int updateBannerStatus(MallBanner banner); int deleteBannerById(Long id); }
