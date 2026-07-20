package com.ruoyi.service;

import com.ruoyi.domain.FactCardTransactions;
import java.util.List;
import java.util.Map;

/**
 * 公交 IC 卡交易 Service 接口
 * 
 * @author 若依
 */
public interface FactCardTransactionsService {
    
    /**
     * 查询 IC 卡交易列表
     * 
     * @param factCardTransactions IC 卡交易信息
     * @return IC 卡交易集合
     */
    public List<FactCardTransactions> selectFactCardTransactionsList(FactCardTransactions factCardTransactions);
    
    /**
     * 按卡种统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 卡种客流量统计
     */
    public List<Map<String, Object>> getFlowByCardType(Integer year, Integer month);
    
    /**
     * 获取换乘率 TOP10 站点
     * 
     * @param limit 限制数量
     * @return 换乘率 TOP10 站点
     */
    public List<Map<String, Object>> getTopTransferStations(Integer limit);
    
    /**
     * 按站点统计卡种分布
     * 
     * @param stopName 站点名称
     * @param year 年份
     * @param month 月份
     * @return 卡种分布
     */
    public List<Map<String, Object>> getCardTypeDistByStation(String stopName, Integer year, Integer month);
    
    /**
     * 获取线路卡种客流对比
     * 
     * @param lineName 线路名称
     * @param year 年份
     * @param month 月份
     * @return 卡种客流对比
     */
    public List<Map<String, Object>> getCardTypeFlowByLine(String lineName, Integer year, Integer month);
    
    /**
     * 获取日均交易量趋势
     * 
     * @param days 天数
     * @return 日均交易量趋势
     */
    public List<Map<String, Object>> getDailyTransactionTrend(Integer days);
}
