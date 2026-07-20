package com.ruoyi.domain;

import lombok.Data;

/**
 * 公交站点维度表
 * 对应表：dim_stops
 * @author 自动生成
 */
@Data
public class DimStops {
    /** 站点名称（唯一） */
    private String stopName;

    /** 站点具体地址 */
    private String stopAddress;

    /** 站点地理经度（如116.990553） */
    private Double longitude;

    /** 站点地理纬度（如36.666514） */
    private Double latitude;

    /** 站点所在行政区域代码（如370104.0） */
    private Double zoneCode;

    /** 站点属性类型（residential=居民区/school=学校/general=一般区域） */
    private String zoneType;
}