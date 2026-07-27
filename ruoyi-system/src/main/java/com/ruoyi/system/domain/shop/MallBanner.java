package com.ruoyi.system.domain.shop;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class MallBanner extends BaseEntity
{
    private Long bannerId;
    @Excel(name = "标题")
    private String title;
    private String imageUrl;
    @Excel(name = "跳转类型", readConverterExp = "NONE=不跳转,PRODUCT=商品,CATEGORY=分类,PATH=小程序路径")
    private String jumpType;
    @Excel(name = "跳转目标")
    private String targetValue;
    @Excel(name = "排序", cellType = Excel.ColumnType.NUMERIC)
    private Integer sortNum;
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;
    @Excel(name = "展示开始", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @Excel(name = "展示结束", dateFormat = "yyyy-MM-dd HH:mm:ss")
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
