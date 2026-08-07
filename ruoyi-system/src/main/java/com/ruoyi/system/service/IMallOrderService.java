package com.ruoyi.system.service;
import java.util.List;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderCloseResult;
import com.ruoyi.system.domain.shop.MallOrderCreateRequest;
public interface IMallOrderService { List<MallOrder> selectOrderList(MallOrder order); MallOrder selectOrderById(Long orderId); MallOrder selectOrderSensitiveById(Long orderId); Long calculateFreightAmount(Long productAmount); MallOrder createOrder(MallOrderCreateRequest request); int paySuccess(Long orderId,String payChannel,String channelTradeNo,Long paidAmount,String operatorType,String operatorId); int cancelOrder(Long orderId,String reason,String operatorType,String operatorId); MallOrderCloseResult closeExpiredOrders(); int shipOrder(Long orderId,String company,String logisticsNo,String operatorType,String operatorId); int completeOrder(Long orderId,String operatorType,String operatorId); }
