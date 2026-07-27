package com.ruoyi.system.domain.shop.app;

public class AppCartPreviewItemDto extends AppProductSkuDto {
    private Long productId; private String productName; private Integer quantity; private Long lineAmountCent; private Boolean available; private String unavailableReason;
    public Long getProductId(){return productId;} public void setProductId(Long value){productId=value;}
    public String getProductName(){return productName;} public void setProductName(String value){productName=value;}
    public Integer getQuantity(){return quantity;} public void setQuantity(Integer value){quantity=value;}
    public Long getLineAmountCent(){return lineAmountCent;} public void setLineAmountCent(Long value){lineAmountCent=value;}
    public Boolean getAvailable(){return available;} public void setAvailable(Boolean value){available=value;}
    public String getUnavailableReason(){return unavailableReason;} public void setUnavailableReason(String value){unavailableReason=value;}
}
