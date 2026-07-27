package com.ruoyi.system.mapper.shop;
import java.util.List; import com.ruoyi.system.domain.shop.MallCoupon;
public interface MallCouponMapper { List<MallCoupon> selectCouponList(MallCoupon c); MallCoupon selectCouponById(Long id); int insertCoupon(MallCoupon c); int updateCoupon(MallCoupon c); int updateCouponStatus(MallCoupon c); int deleteCouponById(Long id); }
