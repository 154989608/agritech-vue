package com.ruoyi.system.domain.shop.app;

import javax.validation.constraints.Size;

public class AppMemberOrderCancelRequest { @Size(max=200) private String cancelReason; public String getCancelReason(){return cancelReason;} public void setCancelReason(String v){cancelReason=v;} }
