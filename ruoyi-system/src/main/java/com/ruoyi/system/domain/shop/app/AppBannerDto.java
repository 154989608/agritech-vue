package com.ruoyi.system.domain.shop.app;

public class AppBannerDto {
    private Long bannerId; private String title; private String imageUrl; private String jumpType; private String targetValue;
    public Long getBannerId(){return bannerId;} public void setBannerId(Long value){bannerId=value;}
    public String getTitle(){return title;} public void setTitle(String value){title=value;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String value){imageUrl=value;}
    public String getJumpType(){return jumpType;} public void setJumpType(String value){jumpType=value;}
    public String getTargetValue(){return targetValue;} public void setTargetValue(String value){targetValue=value;}
}
