package com.ruoyi.web.controller.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.framework.security.context.AppMemberContext;
import com.ruoyi.system.domain.shop.MallCoupon;
import com.ruoyi.system.domain.shop.MallMemberCoupon;
import com.ruoyi.system.domain.shop.app.AppMemberCouponDto;
import com.ruoyi.system.mapper.shop.MallMemberCouponMapper;
import com.ruoyi.system.service.IMallMemberCouponService;

@RestController @RequestMapping("/app/shop") public class AppMemberCouponController { @Autowired private IMallMemberCouponService memberCouponService; @Autowired private MallMemberCouponMapper memberCouponMapper; @Anonymous @GetMapping("/coupons") public AjaxResult available(){Long memberId=AppMemberContext.getMemberIdOrNull();List<MallCoupon> coupons=memberCouponMapper.selectAvailableCoupons();Set<Long> received=new HashSet<Long>();if(memberId!=null&&!coupons.isEmpty()){List<Long> couponIds=new ArrayList<Long>();for(MallCoupon coupon:coupons)couponIds.add(coupon.getCouponId());received.addAll(memberCouponMapper.selectReceivedCouponIds(memberId,couponIds));}List<AppMemberCouponDto> result=new ArrayList<AppMemberCouponDto>();for(MallCoupon coupon:coupons){AppMemberCouponDto dto=toDto(coupon);dto.setReceived(received.contains(coupon.getCouponId()));result.add(dto);}return AjaxResult.success(result);}@GetMapping("/member/coupons") public AjaxResult mine(@RequestParam(required=false) String status){MallMemberCoupon query=new MallMemberCoupon();query.setMemberId(AppMemberContext.getMemberId());query.setStatus(status);List<AppMemberCouponDto> result=new ArrayList<AppMemberCouponDto>();for(MallMemberCoupon coupon:memberCouponService.selectMemberCouponList(query))result.add(toDto(coupon));return AjaxResult.success(result);}@RateLimiter(time=60,count=10,limitType=LimitType.IP) @PostMapping("/coupons/{couponId}/claim") public AjaxResult claim(@PathVariable Long couponId){return AjaxResult.success(toDto(memberCouponService.receiveCoupon(couponId,AppMemberContext.getMemberId())));}private AppMemberCouponDto toDto(MallCoupon source){AppMemberCouponDto result=new AppMemberCouponDto();result.setCouponId(source.getCouponId());result.setCouponName(source.getCouponName());result.setThresholdAmount(source.getThresholdAmount());result.setDiscountAmount(source.getDiscountAmount());result.setStatus(source.getStatus());result.setReceiveEndTime(source.getReceiveEndTime());return result;}private AppMemberCouponDto toDto(MallMemberCoupon source){AppMemberCouponDto result=new AppMemberCouponDto();result.setCouponId(source.getCouponId());result.setMemberCouponId(source.getMemberCouponId());result.setCouponNameSnapshot(source.getCouponNameSnapshot());result.setThresholdAmountSnapshot(source.getThresholdAmountSnapshot());result.setDiscountAmountSnapshot(source.getDiscountAmountSnapshot());result.setStatus(source.getStatus());result.setValidEndTime(source.getValidEndTime());return result;} }
