package com.ruoyi.system.domain.shop.app;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AppMemberLoginRequest { @NotBlank(message="微信登录凭证不能为空") @Size(max=256,message="微信登录凭证非法") private String code;
    public String getCode(){return code;} public void setCode(String value){code=value;} }
