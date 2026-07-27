package com.ruoyi.system.domain.shop;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

public class MallBanner extends BaseEntity
{
    private Long bannerId;
    private String title;
    private String imageUrl;
    private String jumpType;
    private String targetValue;
    private Integer sortNum;
    private String status;
    private Date beginTime;
    private Date endTime;
    public Long getBannerId() { return bannerId; } public void setBannerId(Long v) { bannerId = v; }
    public String getTitle() { return title; } public void setTitle(String v) { title = v; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String v) { imageUrl = v; }
    public String getJumpType() { return jumpType; } public void setJumpType(String v) { jumpType = v; }
    public String getTargetValue() { return targetValue; } public void setTargetValue(String v) { targetValue = v; }
    public Integer getSortNum() { return sortNum; } public void setSortNum(Integer v) { sortNum = v; }
    public String getStatus() { return status; } public void setStatus(String v) { status = v; }
    public Date getBeginTime() { return beginTime; } public void setBeginTime(Date v) { beginTime = v; }
    public Date getEndTime() { return endTime; } public void setEndTime(Date v) { endTime = v; }
}
