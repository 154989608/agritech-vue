package com.ruoyi.quartz.task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.system.service.IMallMemberCouponService;
import com.ruoyi.system.service.IMallOrderService;
@Component("shopTask") public class ShopTask { @Autowired private IMallOrderService orderService; @Autowired private IMallMemberCouponService memberCouponService; public void closeExpiredOrders(){orderService.closeExpiredOrders(); memberCouponService.expireCoupons();} }
