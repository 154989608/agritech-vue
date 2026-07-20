package com.ruoyi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公交线路班次维度表
 * 对应表：dim_schedules
 * @author 自动生成
 */
@Data
public class DimSchedules {
    /** 发车序号 */
    private Integer departSeq;

    /** 计划发车时间 */
    private LocalDateTime planDepartTime;

    /** 班次作废标记（1=作废，0=正常） */
    private Integer cancelFlag;

    /** 是否环形线路（1=是，0=否） */
    private Integer isCircular;

    /** 行驶方向（如上/下/未知） */
    private String direction;

    /** 所属公交线路 */
    private String lineName;

    /** 行程类型（常规/加班等） */
    private String tripType;

    /** 计划到达终点站时间 */
    private LocalDateTime planArriveTime;

    /** 班次编号 */
    private Integer scheduleNum;

    /** 班次始发站 */
    private String startStopName;

    /** 班次终到站 */
    private String endStopName;
}