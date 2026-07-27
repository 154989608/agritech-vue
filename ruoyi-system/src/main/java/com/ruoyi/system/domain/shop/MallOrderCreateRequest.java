package com.ruoyi.system.domain.shop;

import java.util.List;

/** 客户端创建订单输入；金额与商品快照由服务端计算。 */
public class MallOrderCreateRequest
{
    private Long memberId;
    private String clientRequestNo;
    private Long addressId;
    private Long memberCouponId;
    private String buyerRemark;
    private List<Item> items;
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long value) { memberId = value; }
    public String getClientRequestNo() { return clientRequestNo; }
    public void setClientRequestNo(String value) { clientRequestNo = value; }
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long value) { addressId = value; }
    public Long getMemberCouponId() { return memberCouponId; }
    public void setMemberCouponId(Long value) { memberCouponId = value; }
    public String getBuyerRemark() { return buyerRemark; }
    public void setBuyerRemark(String value) { buyerRemark = value; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> value) { items = value; }
    public static class Item { private Long skuId; private Integer quantity; public Long getSkuId() { return skuId; } public void setSkuId(Long value) { skuId = value; } public Integer getQuantity() { return quantity; } public void setQuantity(Integer value) { quantity = value; } }
}
