package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.shop.MallMemberAddress;
import com.ruoyi.system.domain.shop.MallMemberCoupon;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderCreateRequest;
import com.ruoyi.system.domain.shop.MallOrderItem;
import com.ruoyi.system.domain.shop.app.AppCartPreviewDto;
import com.ruoyi.system.domain.shop.app.AppCartPreviewRequest;
import com.ruoyi.system.domain.shop.app.AppMemberOrderDto;
import com.ruoyi.system.domain.shop.app.AppMemberOrderItemDto;
import com.ruoyi.system.domain.shop.app.AppMemberOrderPreviewDto;
import com.ruoyi.system.domain.shop.app.AppMemberOrderRequest;
import com.ruoyi.system.mapper.shop.MallOrderMapper;
import com.ruoyi.system.mapper.shop.MallMemberCouponMapper;
import com.ruoyi.system.service.IAppMemberOrderService;
import com.ruoyi.system.service.IAppShopService;
import com.ruoyi.system.service.IMallOrderService;

@Service public class AppMemberOrderServiceImpl implements IAppMemberOrderService {
 @Autowired private IAppShopService shopService; @Autowired private IMallOrderService orderService; @Autowired private MallOrderMapper orderMapper; @Autowired private MallMemberCouponMapper memberCouponMapper;
 public AppMemberOrderPreviewDto preview(Long memberId,AppMemberOrderRequest request){if(orderMapper.selectMemberAddress(memberId,request.getAddressId())==null)throw new ServiceException("收货地址不存在");AppCartPreviewRequest cart=new AppCartPreviewRequest();List<AppCartPreviewRequest.Item> items=new ArrayList<AppCartPreviewRequest.Item>();for(AppMemberOrderRequest.Item source:request.getItems()){AppCartPreviewRequest.Item item=new AppCartPreviewRequest.Item();item.setSkuId(source.getSkuId());item.setQuantity(source.getQuantity());items.add(item);}cart.setItems(items);AppCartPreviewDto current=shopService.previewCart(cart);AppMemberOrderPreviewDto result=new AppMemberOrderPreviewDto();result.setItems(current.getItems());result.setProductAmountCent(current.getProductAmountCent());Long freightAmount=orderService.calculateFreightAmount(current.getProductAmountCent());long discountAmount=0L;if(request.getMemberCouponId()!=null){MallMemberCoupon coupon=memberCouponMapper.selectAvailableById(request.getMemberCouponId(),memberId);if(coupon==null||coupon.getThresholdAmountSnapshot()>current.getProductAmountCent())throw new ServiceException("优惠券不可用于当前订单");discountAmount=Math.min(coupon.getDiscountAmountSnapshot(),current.getProductAmountCent());}result.setFreightAmountCent(freightAmount);result.setDiscountAmountCent(discountAmount);result.setPayableAmountCent(current.getProductAmountCent()+freightAmount-discountAmount);for(com.ruoyi.system.domain.shop.app.AppCartPreviewItemDto item:current.getItems())if(!Boolean.TRUE.equals(item.getAvailable())){result.setUnavailableReason(item.getUnavailableReason());break;}return result;}
 public AppMemberOrderDto createOrder(Long memberId,AppMemberOrderRequest request){MallOrderCreateRequest internal=new MallOrderCreateRequest();internal.setMemberId(memberId);internal.setClientRequestNo(request.getClientRequestNo());internal.setAddressId(request.getAddressId());internal.setMemberCouponId(request.getMemberCouponId());internal.setBuyerRemark(request.getBuyerRemark());List<MallOrderCreateRequest.Item> items=new ArrayList<MallOrderCreateRequest.Item>();for(AppMemberOrderRequest.Item source:request.getItems()){MallOrderCreateRequest.Item item=new MallOrderCreateRequest.Item();item.setSkuId(source.getSkuId());item.setQuantity(source.getQuantity());items.add(item);}internal.setItems(items);return toDto(orderService.createOrder(internal));}
 public List<MallOrder> list(Long memberId,String status){MallOrder query=new MallOrder();query.setMemberId(memberId);query.setOrderStatus(status);List<MallOrder> orders=orderMapper.selectOrderList(query);for(MallOrder order:orders)order.setItems(orderMapper.selectOrderItems(order.getOrderId()));return orders;}
 public List<AppMemberOrderDto> toDtos(List<MallOrder> orders){List<AppMemberOrderDto> result=new ArrayList<AppMemberOrderDto>();for(MallOrder order:orders)result.add(toDto(order));return result;}
 public AppMemberOrderDto detail(Long memberId,String orderNo){MallOrder order=orderMapper.selectOrderByMemberNo(memberId,orderNo);if(order==null)throw new ServiceException("订单不存在");return toDto(orderService.selectOrderSensitiveById(order.getOrderId()));}
 public int cancel(Long memberId,String orderNo,String reason){MallOrder order=orderMapper.selectOrderByMemberNo(memberId,orderNo);if(order==null)throw new ServiceException("订单不存在");return orderService.cancelOrder(order.getOrderId(),StringUtils.isBlank(reason)?"会员取消订单":reason,String.valueOf(memberId));}
 public int confirmReceipt(Long memberId,String orderNo){MallOrder order=orderMapper.selectOrderByMemberNo(memberId,orderNo);if(order==null)throw new ServiceException("订单不存在");return orderService.completeOrder(order.getOrderId(),String.valueOf(memberId));}
 private AppMemberOrderDto toDto(MallOrder source){AppMemberOrderDto result=new AppMemberOrderDto();result.setOrderNo(source.getOrderNo());result.setOrderStatus(source.getOrderStatus());result.setProductAmount(source.getProductAmount());result.setFreightAmount(source.getFreightAmount());result.setDiscountAmount(source.getDiscountAmount());result.setPayableAmount(source.getPayableAmount());result.setReceiverName(source.getReceiverName());result.setReceiverPhone(source.getReceiverPhone());result.setProvinceName(source.getProvinceName());result.setCityName(source.getCityName());result.setDistrictName(source.getDistrictName());result.setDetailAddress(source.getDetailAddress());result.setLogisticsCompany(source.getLogisticsCompany());result.setLogisticsNo(source.getLogisticsNo());result.setBuyerRemark(source.getBuyerRemark());result.setCancelReason(source.getCancelReason());result.setCreateTime(source.getCreateTime());List<AppMemberOrderItemDto> items=new ArrayList<AppMemberOrderItemDto>();for(MallOrderItem sourceItem:source.getItems()==null?new ArrayList<MallOrderItem>():source.getItems()){AppMemberOrderItemDto item=new AppMemberOrderItemDto();item.setOrderItemId(sourceItem.getOrderItemId());item.setSkuId(sourceItem.getSkuId());item.setProductNameSnapshot(sourceItem.getProductNameSnapshot());item.setSkuNameSnapshot(sourceItem.getSkuNameSnapshot());item.setImageUrlSnapshot(sourceItem.getImageUrlSnapshot());item.setSalePriceSnapshot(sourceItem.getSalePriceSnapshot());item.setQuantity(sourceItem.getQuantity());item.setPayableAmount(sourceItem.getPayableAmount());items.add(item);}result.setItems(items);result.setAvailableActions(actionsFor(source.getOrderStatus()));return result;}
 private List<String> actionsFor(String status){if("PENDING_PAYMENT".equals(status))return Arrays.asList("CANCEL","PAY");if("SHIPPED".equals(status))return Arrays.asList("CONFIRM_RECEIPT");return new ArrayList<String>();}
}
