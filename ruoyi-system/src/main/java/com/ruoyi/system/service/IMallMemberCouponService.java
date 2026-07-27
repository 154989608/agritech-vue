package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.shop.MallMemberCoupon;

public interface IMallMemberCouponService
{
    MallMemberCoupon receiveCoupon(Long couponId, Long memberId);

    List<MallMemberCoupon> selectMemberCouponList(MallMemberCoupon coupon);

    int lockCoupon(Long memberCouponId, Long memberId, Long orderId, Long orderAmount);

    int expireCoupons();
}
