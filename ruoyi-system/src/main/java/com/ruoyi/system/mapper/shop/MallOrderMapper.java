package com.ruoyi.system.mapper.shop;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.shop.MallMember;
import com.ruoyi.system.domain.shop.MallMemberAddress;
import com.ruoyi.system.domain.shop.MallMemberCoupon;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderItem;
import com.ruoyi.system.domain.shop.MallProductSku;
public interface MallOrderMapper {
    List<MallOrder> selectOrderList(MallOrder order); List<MallOrder> selectExpiredPendingOrders(); MallOrder selectOrderById(Long orderId); MallOrder selectOrderByMemberRequest(@Param("memberId") Long memberId,@Param("clientRequestNo") String clientRequestNo); List<MallOrderItem> selectOrderItems(Long orderId);
    MallMember selectActiveMember(Long memberId); MallMemberAddress selectMemberAddress(@Param("memberId") Long memberId,@Param("addressId") Long addressId); MallOrderItem selectSkuForOrder(Long skuId); MallMemberCoupon selectMemberCouponForUpdate(@Param("memberCouponId") Long memberCouponId,@Param("memberId") Long memberId);
    int insertOrder(MallOrder order); int insertOrderItem(MallOrderItem item); int lockSkuStock(@Param("skuId") Long skuId,@Param("quantity") Integer quantity); int insertInventoryBizLog(@Param("skuId") Long skuId,@Param("bizType") String bizType,@Param("bizNo") String bizNo,@Param("availableBefore") Long availableBefore,@Param("availableAfter") Long availableAfter,@Param("lockedBefore") Long lockedBefore,@Param("lockedAfter") Long lockedAfter,@Param("operatorType") String operatorType,@Param("operatorId") String operatorId,@Param("reason") String reason);
    int lockCoupon(@Param("memberCouponId") Long memberCouponId,@Param("memberId") Long memberId,@Param("orderId") Long orderId,@Param("orderAmount") Long orderAmount); int payPendingOrder(@Param("orderId") Long orderId,@Param("payChannel") String payChannel,@Param("channelTradeNo") String channelTradeNo,@Param("paidAmount") Long paidAmount); int deductLockedStock(@Param("skuId") Long skuId,@Param("quantity") Integer quantity); int useCoupon(Long orderId); int increaseCouponUsed(Long orderId);
    int cancelPendingOrder(@Param("orderId") Long orderId,@Param("reason") String reason,@Param("updateBy") String updateBy);
    int shipPendingOrder(@Param("orderId") Long orderId,@Param("company") String company,@Param("logisticsNo") String logisticsNo,@Param("updateBy") String updateBy);
    int completeShippedOrder(@Param("orderId") Long orderId, @Param("updateBy") String updateBy);
    MallProductSku selectSkuStockForUpdate(Long skuId); int releaseSkuStock(@Param("skuId") Long skuId,@Param("quantity") Integer quantity);
    int unlockCoupon(Long orderId); int insertOrderLog(@Param("orderId") Long orderId,@Param("action") String action,@Param("beforeStatus") String beforeStatus,@Param("afterStatus") String afterStatus,@Param("operatorType") String operatorType,@Param("operatorId") String operatorId,@Param("description") String description);
    int insertInventoryLog(@Param("skuId") Long skuId,@Param("bizNo") String bizNo,@Param("availableBefore") Long availableBefore,@Param("availableAfter") Long availableAfter,@Param("lockedBefore") Long lockedBefore,@Param("lockedAfter") Long lockedAfter,@Param("operatorId") String operatorId);
    int increaseSkuStock(@Param("skuId") Long skuId,@Param("quantity") Long quantity);
    int insertInventoryAdjustLog(@Param("skuId") Long skuId,@Param("bizType") String bizType,@Param("reason") String reason,@Param("availableBefore") Long availableBefore,@Param("availableAfter") Long availableAfter,@Param("lockedStock") Long lockedStock,@Param("operatorId") String operatorId);
}
