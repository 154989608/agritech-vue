package com.ruoyi.system.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderItem;
import com.ruoyi.system.domain.shop.MallProductSku;
import com.ruoyi.system.mapper.shop.MallOrderMapper;
import com.ruoyi.system.service.IMallOrderService;
@Service public class MallOrderServiceImpl implements IMallOrderService {
 @Autowired private MallOrderMapper orderMapper;
 public List<MallOrder> selectOrderList(MallOrder order){List<MallOrder> list=orderMapper.selectOrderList(order);for(MallOrder item:list){item.setReceiverPhone(mask(item.getReceiverPhone()));item.setDetailAddress("***");}return list;} public MallOrder selectOrderById(Long id){MallOrder order=selectOrderSensitiveById(id);if(order!=null){order.setReceiverPhone(mask(order.getReceiverPhone()));order.setDetailAddress("***");}return order;} public MallOrder selectOrderSensitiveById(Long id){MallOrder order=orderMapper.selectOrderById(id);if(order!=null)order.setItems(orderMapper.selectOrderItems(id));return order;} private String mask(String phone){return phone==null?null:phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");}
 @Transactional(rollbackFor=Exception.class) public int cancelOrder(Long id,String reason,String operator){MallOrder order=selectOrderById(id);if(order==null)throw new ServiceException("订单不存在");if(orderMapper.cancelPendingOrder(id,reason,operator)==0)throw new ServiceException("仅待支付订单可以取消");for(MallOrderItem item:order.getItems()){MallProductSku sku=orderMapper.selectSkuStockForUpdate(item.getSkuId());if(sku==null||sku.getLockedStock()<item.getQuantity())throw new ServiceException("订单库存状态异常");if(orderMapper.releaseSkuStock(item.getSkuId(),item.getQuantity())==0)throw new ServiceException("库存释放失败");orderMapper.insertInventoryLog(item.getSkuId(),order.getOrderNo(),sku.getAvailableStock(),sku.getAvailableStock()+item.getQuantity(),sku.getLockedStock(),sku.getLockedStock()-item.getQuantity(),operator);}orderMapper.unlockCoupon(id);orderMapper.insertOrderLog(id,"CANCEL","PENDING_PAYMENT","CANCELED","ADMIN",operator,"后台取消待支付订单");return 1;}
 @Transactional(rollbackFor=Exception.class) public int closeExpiredOrders(){int count=0;for(MallOrder order:orderMapper.selectExpiredPendingOrders()){try{cancelOrder(order.getOrderId(),"订单支付超时关闭","SYSTEM");count++;}catch(ServiceException ignored){}}return count;}
 @Transactional(rollbackFor=Exception.class) public int shipOrder(Long id,String company,String logisticsNo,String operator){if(company==null||company.trim().isEmpty()||logisticsNo==null||logisticsNo.trim().isEmpty())throw new ServiceException("物流公司和运单号不能为空");if(orderMapper.shipPendingOrder(id,company,logisticsNo,operator)==0)throw new ServiceException("仅待发货订单可以发货");orderMapper.insertOrderLog(id,"SHIP","PENDING_SHIPMENT","SHIPPED","ADMIN",operator,"后台订单发货");return 1;}
}
