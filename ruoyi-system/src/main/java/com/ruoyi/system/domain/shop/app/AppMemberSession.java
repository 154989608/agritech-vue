package com.ruoyi.system.domain.shop.app;

import java.io.Serializable;

public class AppMemberSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long memberId; private String appId;
    public Long getMemberId(){return memberId;} public void setMemberId(Long value){memberId=value;}
    public String getAppId(){return appId;} public void setAppId(String value){appId=value;}
}
