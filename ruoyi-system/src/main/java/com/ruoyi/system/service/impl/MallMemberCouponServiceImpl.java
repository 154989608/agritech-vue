package com.ruoyi.system.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.shop.MallCoupon;
import com.ruoyi.system.domain.shop.MallMemberCoupon;
import com.ruoyi.system.mapper.shop.MallMemberCouponMapper;
import com.ruoyi.system.service.IMallMemberCouponService;

@Service
public class MallMemberCouponServiceImpl implements IMallMemberCouponService
{
    @Autowired
    private MallMemberCouponMapper memberCouponMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MallMemberCoupon receiveCoupon(Long couponId, Long memberId)
    {
        if (couponId == null || memberId == null)
        {
            throw new ServiceException("优惠券或会员不能为空");
        }
        MallCoupon coupon = memberCouponMapper.selectCouponForReceive(couponId);
        Date now = new Date();
        if (coupon == null || !"0".equals(coupon.getStatus()) || now.before(coupon.getReceiveBeginTime()) || !now.before(coupon.getReceiveEndTime()))
        {
            throw new ServiceException("优惠券当前不可领取");
        }
        if (memberCouponMapper.countByCouponAndMember(couponId, memberId) >= coupon.getLimitPerMember())
        {
            throw new ServiceException("已达到每人限领数量");
        }
        if (memberCouponMapper.increaseReceived(couponId) == 0)
        {
            throw new ServiceException("优惠券已领完");
        }
        MallMemberCoupon memberCoupon = snapshot(coupon, memberId, now);
        memberCouponMapper.insertMemberCoupon(memberCoupon);
        return memberCoupon;
    }

    @Override
    public List<MallMemberCoupon> selectMemberCouponList(MallMemberCoupon coupon)
    {
        return memberCouponMapper.selectMemberCouponList(coupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int lockCoupon(Long memberCouponId, Long memberId, Long orderId, Long orderAmount)
    {
        if (memberCouponId == null || memberId == null || orderId == null || orderAmount == null || orderAmount < 0)
        {
            throw new ServiceException("锁定优惠券参数非法");
        }
        if (memberCouponMapper.lockCoupon(memberCouponId, memberId, orderId, orderAmount) == 0)
        {
            throw new ServiceException("优惠券不可用于当前订单");
        }
        return 1;
    }

    @Override
    public int expireCoupons()
    {
        return memberCouponMapper.expireCoupons();
    }

    private MallMemberCoupon snapshot(MallCoupon coupon, Long memberId, Date now)
    {
        MallMemberCoupon memberCoupon = new MallMemberCoupon();
        memberCoupon.setCouponNo(IdUtils.fastSimpleUUID());
        memberCoupon.setCouponId(coupon.getCouponId());
        memberCoupon.setMemberId(memberId);
        memberCoupon.setCouponNameSnapshot(coupon.getCouponName());
        memberCoupon.setThresholdAmountSnapshot(coupon.getThresholdAmount());
        memberCoupon.setDiscountAmountSnapshot(coupon.getDiscountAmount());
        memberCoupon.setStatus("AVAILABLE");
        if (coupon.getValidDays() != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DATE, coupon.getValidDays());
            memberCoupon.setValidBeginTime(now);
            memberCoupon.setValidEndTime(calendar.getTime());
        }
        else
        {
            memberCoupon.setValidBeginTime(coupon.getValidBeginTime());
            memberCoupon.setValidEndTime(coupon.getValidEndTime());
        }
        return memberCoupon;
    }
}
