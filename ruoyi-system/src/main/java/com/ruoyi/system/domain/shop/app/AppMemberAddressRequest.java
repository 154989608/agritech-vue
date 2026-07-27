package com.ruoyi.system.domain.shop.app;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AppMemberAddressRequest {
    @NotBlank @Size(max=64) private String receiverName; @NotBlank @Pattern(regexp="^1[3-9]\\d{9}$",message="收货电话格式不正确") private String receiverPhone;
    @NotBlank @Size(max=32) private String provinceCode; @NotBlank @Size(max=64) private String provinceName; @NotBlank @Size(max=32) private String cityCode; @NotBlank @Size(max=64) private String cityName; @NotBlank @Size(max=32) private String districtCode; @NotBlank @Size(max=64) private String districtName; @NotBlank @Size(max=255) private String detailAddress; private Boolean isDefault;
    public String getReceiverName(){return receiverName;} public void setReceiverName(String v){receiverName=v;} public String getReceiverPhone(){return receiverPhone;} public void setReceiverPhone(String v){receiverPhone=v;} public String getProvinceCode(){return provinceCode;} public void setProvinceCode(String v){provinceCode=v;} public String getProvinceName(){return provinceName;} public void setProvinceName(String v){provinceName=v;} public String getCityCode(){return cityCode;} public void setCityCode(String v){cityCode=v;} public String getCityName(){return cityName;} public void setCityName(String v){cityName=v;} public String getDistrictCode(){return districtCode;} public void setDistrictCode(String v){districtCode=v;} public String getDistrictName(){return districtName;} public void setDistrictName(String v){districtName=v;} public String getDetailAddress(){return detailAddress;} public void setDetailAddress(String v){detailAddress=v;} public Boolean getIsDefault(){return isDefault;} public void setIsDefault(Boolean v){isDefault=v;}
}
