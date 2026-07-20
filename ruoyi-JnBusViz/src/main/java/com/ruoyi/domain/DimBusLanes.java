package com.ruoyi.domain;

import lombok.Data;

/**
 * 公交专用道维度表
 * 对应表：dim_bus_lanes
 * @author 自动生成
 */
@Data
public class DimBusLanes {
    /** 道路名称（如经十路） */
    private String roadName;

    /** 专用道起点（如邢州立交桥） */
    private String startPoint;

    /** 专用道终点（如邢州立交桥） */
    private String endPoint;

    /** 专用道类型（如主干道、次干道、支路） */
    private String laneType;

    /** 专用道长度（单位：公里） */
    private Float lengthKm;

    /** 行驶方向（如上行/下行） */
    private String direction;

    /** 限行时间（如 6:00-20:00） */
    private String timeLimit;

    /** 地理经度 */
    private Double longitude;

    /** 地理纬度 */
    private Double latitude;

    /** 专用道类型（如 1、3，代表不同规格）-兼容字段 */
    private Integer routeType;

    /** 专用道所属路线名称（如经十路）-兼容字段 */
    private String routeName;

    /** 地理要素类型（如 LineString）-兼容字段 */
    private String featureType;

    /** 要素坐标集合（JSON 格式字符串）-兼容字段 */
    private String featureCoords;
}