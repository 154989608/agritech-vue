package com.ruoyi.system.service;
import java.util.List; import com.ruoyi.system.domain.shop.MallCoupon;
public interface IMallCouponService { List<MallCoupon> selectCouponList(MallCoupon c); MallCoupon selectCouponById(Long id); int insertCoupon(MallCoupon c); int updateCoupon(MallCoupon c); int updateStatus(Long id,String status,String user); int deleteCoupon(Long id); }
