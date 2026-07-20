package com.ruoyi.domain;

import lombok.Data;

/**
 * 公交线路站点详情维度表
 * 对应表：dim_route_station_details
 * @author 自动生成
 */
@Data
public class DimRouteStationDetails {
    /** 线路名称 */
    private String lineName;

    /** 站点名称 */
    private String stopName;

    /** 站点顺序 */
    private Integer stopOrder;

    /** 行驶方向（上/下） */
    private String direction;

    /** 站点类型（首末站/中途站） */
    private String stopType;

    /** 首班车时间 */
    private String firstBusTime;

    /** 末班车时间 */
    private String lastBusTime;
}
