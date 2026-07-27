package com.ruoyi.web.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.security.context.AppMemberContext;
import com.ruoyi.system.domain.shop.MallMember;
import com.ruoyi.system.domain.shop.app.AppMemberProfileDto;
import com.ruoyi.system.mapper.shop.MallMemberCouponMapper;
import com.ruoyi.system.mapper.shop.MallMemberMapper;
import com.ruoyi.system.mapper.shop.MallOrderMapper;

@RestController @RequestMapping("/app/shop") public class AppMemberProfileController {
 @Autowired private MallMemberMapper memberMapper; @Autowired private MallOrderMapper orderMapper; @Autowired private MallMemberCouponMapper couponMapper;
 @GetMapping("/me") public AjaxResult me(){Long memberId=AppMemberContext.getMemberId();MallMember member=memberMapper.selectMemberById(memberId);AppMemberProfileDto result=new AppMemberProfileDto();result.setNickname(member==null?"":member.getNickname());result.setPendingPaymentCount(orderMapper.countOrdersByMemberStatus(memberId,"PENDING_PAYMENT"));result.setPendingShipmentCount(orderMapper.countOrdersByMemberStatus(memberId,"PENDING_SHIPMENT"));result.setShippedCount(orderMapper.countOrdersByMemberStatus(memberId,"SHIPPED"));result.setCompletedCount(orderMapper.countOrdersByMemberStatus(memberId,"COMPLETED"));result.setAvailableCouponCount(couponMapper.countAvailableByMember(memberId));return AjaxResult.success(result);}
}
