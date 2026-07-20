package com.ruoyi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 公交IC卡交易事实表
 * 对应表：fact_card_transactions
 * @author 自动生成
 */
@Data
public class FactCardTransactions {
    /** 交易时间（精确到秒） */
    private LocalDateTime txTime;

    /** IC卡卡号（唯一标识） */
    private String cardId;

    /** 乘坐公交线路名称 */
    private String lineName;

    /** 上车站点名称 */
    private String stopName;

    /** IC卡类型（普通卡/学生卡等） */
    private String cardType;

    /** 线路基础票价（单位：元） */
    private Float basePrice;

    /** 实际交易金额（含折扣，单位：元） */
    private Float actualAmount;

    /** 是否换乘（1=是，0=否） */
    private Integer isTransfer;

    /** 支持的业务政策（售/充/检等） */
    private String policySupport;

    /** 统计年份（如2025） */
    private Integer statYear;

    /** 统计月份（1-12） */
    private Integer statMonth;

    /** 统计日期（1-31） */
    private Integer statDay;

    /** 统计小时（0-23） */
    private Integer statHour;
}