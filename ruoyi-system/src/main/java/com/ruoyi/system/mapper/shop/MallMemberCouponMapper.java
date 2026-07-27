package com.ruoyi.system.mapper.shop;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.shop.MallCoupon;
import com.ruoyi.system.domain.shop.MallMemberCoupon;
public interface MallMemberCouponMapper { MallCoupon selectCouponForReceive(Long couponId); int countByCouponAndMember(@Param("couponId") Long couponId,@Param("memberId") Long memberId); int increaseReceived(Long couponId); int insertMemberCoupon(MallMemberCoupon coupon); List<MallMemberCoupon> selectMemberCouponList(MallMemberCoupon coupon); MallMemberCoupon selectAvailableById(@Param("memberCouponId") Long memberCouponId,@Param("memberId") Long memberId); int lockCoupon(@Param("memberCouponId") Long memberCouponId,@Param("memberId") Long memberId,@Param("orderId") Long orderId,@Param("orderAmount") Long orderAmount); int unlockCoupon(Long orderId); int useCoupon(Long orderId); int expireCoupons(); }
