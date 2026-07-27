package com.ruoyi.system.service;
import java.util.List;
import com.ruoyi.system.domain.shop.MallBanner;
public interface IMallBannerService { List<MallBanner> selectBannerList(MallBanner banner); MallBanner selectBannerById(Long id); int insertBanner(MallBanner banner); int updateBanner(MallBanner banner); int updateStatus(Long id,String status,String user); int deleteBanner(Long id); }
