package com.ruoyi.system.service;
import java.util.List;
import com.ruoyi.system.domain.shop.MallOrder;
public interface IMallOrderService { List<MallOrder> selectOrderList(MallOrder order); MallOrder selectOrderById(Long orderId); MallOrder selectOrderSensitiveById(Long orderId); int cancelOrder(Long orderId,String reason,String operator); int closeExpiredOrders(); int shipOrder(Long orderId,String company,String logisticsNo,String operator); }
