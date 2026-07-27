package com.ruoyi.system.domain.shop;

import java.util.List;
import com.ruoyi.common.core.domain.BaseEntity;

public class MallOrder extends BaseEntity
{
    private Long orderId; private String orderNo; private Long memberId; private String orderStatus;
    private Long productAmount; private Long freightAmount; private Long discountAmount; private Long payableAmount; private Long paidAmount;
    private String receiverName; private String receiverPhone; private String provinceName; private String cityName; private String districtName; private String detailAddress;
    private String logisticsCompany; private String logisticsNo; private String buyerRemark; private String adminRemark; private String cancelReason;
    private List<MallOrderItem> items;
    public Long getOrderId(){return orderId;} public void setOrderId(Long v){orderId=v;} public String getOrderNo(){return orderNo;} public void setOrderNo(String v){orderNo=v;} public Long getMemberId(){return memberId;} public void setMemberId(Long v){memberId=v;} public String getOrderStatus(){return orderStatus;} public void setOrderStatus(String v){orderStatus=v;}
    public Long getProductAmount(){return productAmount;} public void setProductAmount(Long v){productAmount=v;} public Long getFreightAmount(){return freightAmount;} public void setFreightAmount(Long v){freightAmount=v;} public Long getDiscountAmount(){return discountAmount;} public void setDiscountAmount(Long v){discountAmount=v;} public Long getPayableAmount(){return payableAmount;} public void setPayableAmount(Long v){payableAmount=v;} public Long getPaidAmount(){return paidAmount;} public void setPaidAmount(Long v){paidAmount=v;}
    public String getReceiverName(){return receiverName;} public void setReceiverName(String v){receiverName=v;} public String getReceiverPhone(){return receiverPhone;} public void setReceiverPhone(String v){receiverPhone=v;} public String getProvinceName(){return provinceName;} public void setProvinceName(String v){provinceName=v;} public String getCityName(){return cityName;} public void setCityName(String v){cityName=v;} public String getDistrictName(){return districtName;} public void setDistrictName(String v){districtName=v;} public String getDetailAddress(){return detailAddress;} public void setDetailAddress(String v){detailAddress=v;}
    public String getLogisticsCompany(){return logisticsCompany;} public void setLogisticsCompany(String v){logisticsCompany=v;} public String getLogisticsNo(){return logisticsNo;} public void setLogisticsNo(String v){logisticsNo=v;} public String getBuyerRemark(){return buyerRemark;} public void setBuyerRemark(String v){buyerRemark=v;} public String getAdminRemark(){return adminRemark;} public void setAdminRemark(String v){adminRemark=v;} public String getCancelReason(){return cancelReason;} public void setCancelReason(String v){cancelReason=v;} public List<MallOrderItem> getItems(){return items;} public void setItems(List<MallOrderItem> v){items=v;}
}
