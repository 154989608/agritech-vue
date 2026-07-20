package com.ruoyi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公交站点客流事实表
 * 对应表：fact_station_flow
 * @author 自动生成
 */
@Data
public class FactStationFlow {
    /** 客流统计时间（精确到小时） */
    private LocalDateTime statTime;

    /** 站点名称 */
    private String stopName;

    /** 所属公交线路 */
    private String lineName;

    /** 站点区域类型（高校/学区/一般区域等） */
    private String zoneType;

    /** 时段分类（早高峰/晚高峰/平峰等） */
    private String timePeriod;

    /** 进站客流量（人） */
    private Integer boardingCount;

    /** 出站客流量（人） */
    private Integer alightingCount;

    /** 净客流变化（进站-出站） */
    private Integer netFlow;

    /** 车厢满载率（0-1，小数形式） */
    private Float loadFactor;

    /** 同比变化率（正数=增长，负数=下降） */
    private Float yoyRate;

    /** 统计年份 */
    private Integer statYear;

    /** 统计月份 */
    private Integer statMonth;

    /** 统计日期 */
    private Integer statDay;

    /** 统计小时 */
    private Integer statHour;
}