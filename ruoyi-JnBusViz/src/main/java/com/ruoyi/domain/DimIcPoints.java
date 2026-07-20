package com.ruoyi.domain;

import lombok.Data;

/**
 * IC卡服务网点维度表
 * 对应表：dim_ic_points
 * @author 自动生成
 */
@Data
public class DimIcPoints {
    /** 网点唯一 ID（如 1、2） */
    private Integer pointId;

    /** 网点名称（如总公司、马鞍山银座） */
    private String pointName;

    /** 网点类型（如充值点、退卡点、综合点） */
    private String pointType;

    /** 网点具体地址 */
    private String address;

    /** 地理经度 */
    private Double longitude;

    /** 地理纬度 */
    private Double latitude;

    /** 网点营业时间（如 7:30-18:00） */
    private String serviceHours;

    /** 网点联系电话（如 96190） */
    private String tel;

    /** 业务发售范围（如济南公交发行的所有卡类） */
    private String serviceScope;

    /** 网点营业时间（如 7:30-18:00）-兼容字段 */
    private String businessHours;
}