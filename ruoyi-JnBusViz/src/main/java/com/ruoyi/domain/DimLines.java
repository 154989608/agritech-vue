package com.ruoyi.domain;

import lombok.Data;

/**
 * 公交线路维度表
 * 对应表：dim_lines
 * @author 自动生成
 */
@Data
public class DimLines {
    /** 线路唯一ID（如577、612） */
    private String lineId;

    /** 线路名称（如K1路、K2路） */
    private String lineName;

    /** 线路起点站 */
    private String startStop;

    /** 线路终点站 */
    private String endStop;

    /** 夏季首班车时间（如05:00） */
    private String summerDepartTime;

    /** 夏季末班车时间（如22:30） */
    private String summerArriveTime;

    /** 冬季首班车时间（如05:30） */
    private String winterDepartTime;

    /** 冬季末班车时间（如22:00） */
    private String winterArriveTime;

    /** 线路途经站点列表（逗号分隔） */
    private String stopList;
}