package com.ruoyi.system.domain.shop.app;

public class AppProductSkuDto {
    private Long skuId; private String skuName; private String specValuesJson; private String imageUrl; private Long priceCent; private Long marketPriceCent; private Long availableStock; private Boolean soldOut;
    public Long getSkuId(){return skuId;} public void setSkuId(Long value){skuId=value;}
    public String getSkuName(){return skuName;} public void setSkuName(String value){skuName=value;}
    public String getSpecValuesJson(){return specValuesJson;} public void setSpecValuesJson(String value){specValuesJson=value;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String value){imageUrl=value;}
    public Long getPriceCent(){return priceCent;} public void setPriceCent(Long value){priceCent=value;}
    public Long getMarketPriceCent(){return marketPriceCent;} public void setMarketPriceCent(Long value){marketPriceCent=value;}
    public Long getAvailableStock(){return availableStock;} public void setAvailableStock(Long value){availableStock=value;}
    public Boolean getSoldOut(){return soldOut;} public void setSoldOut(Boolean value){soldOut=value;}
}
