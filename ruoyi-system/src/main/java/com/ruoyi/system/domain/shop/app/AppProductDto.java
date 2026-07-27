package com.ruoyi.system.domain.shop.app;

import java.util.ArrayList;
import java.util.List;

public class AppProductDto {
    private Long productId; private Long categoryId; private String productName; private String subtitle; private String mainImage; private String imagesJson; private String detailHtml; private String productParamsJson; private Long priceCent; private Long marketPriceCent; private Boolean soldOut; private List<AppProductSkuDto> skus = new ArrayList<AppProductSkuDto>();
    public Long getProductId(){return productId;} public void setProductId(Long value){productId=value;}
    public Long getCategoryId(){return categoryId;} public void setCategoryId(Long value){categoryId=value;}
    public String getProductName(){return productName;} public void setProductName(String value){productName=value;}
    public String getSubtitle(){return subtitle;} public void setSubtitle(String value){subtitle=value;}
    public String getMainImage(){return mainImage;} public void setMainImage(String value){mainImage=value;}
    public String getImagesJson(){return imagesJson;} public void setImagesJson(String value){imagesJson=value;}
    public String getDetailHtml(){return detailHtml;} public void setDetailHtml(String value){detailHtml=value;}
    public String getProductParamsJson(){return productParamsJson;} public void setProductParamsJson(String value){productParamsJson=value;}
    public Long getPriceCent(){return priceCent;} public void setPriceCent(Long value){priceCent=value;}
    public Long getMarketPriceCent(){return marketPriceCent;} public void setMarketPriceCent(Long value){marketPriceCent=value;}
    public Boolean getSoldOut(){return soldOut;} public void setSoldOut(Boolean value){soldOut=value;}
    public List<AppProductSkuDto> getSkus(){return skus;} public void setSkus(List<AppProductSkuDto> value){skus=value;}
}
