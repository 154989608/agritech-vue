package com.ruoyi.mapper;

import com.ruoyi.domain.FactCardTransactions;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 公交 IC 卡交易 Mapper 接口
 * 
 * @author 若依
 */
public interface FactCardTransactionsMapper {
    
    /**
     * 查询 IC 卡交易列表
     * 
     * @param factCardTransactions IC 卡交易信息
     * @return IC 卡交易集合
     */
    List<FactCardTransactions> selectFactCardTransactionsList(FactCardTransactions factCardTransactions);
    
    /**
     * 按卡种统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 卡种客流量统计
     */
    @MapKey("cardType")
    List<Map<String, Object>> getFlowByCardType(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取换乘率 TOP10 站点
     * 
     * @param limit 限制数量
     * @return 换乘率 TOP10 站点
     */
    @MapKey("stopName")
    List<Map<String, Object>> getTopTransferStations(@Param("limit") Integer limit);
    
    /**
     * 按站点统计卡种分布
     * 
     * @param stopName 站点名称
     * @param year 年份
     * @param month 月份
     * @return 卡种分布
     */
    @MapKey("cardType")
    List<Map<String, Object>> getCardTypeDistByStation(@Param("stopName") String stopName, 
                                                              @Param("year") Integer year, 
                                                              @Param("month") Integer month);
    
    /**
     * 获取线路卡种客流对比
     * 
     * @param lineName 线路名称
     * @param year 年份
     * @param month 月份
     * @return 卡种客流对比
     */
    @MapKey("cardType")
    List<Map<String, Object>> getCardTypeFlowByLine(@Param("lineName") String lineName,
                                                           @Param("year") Integer year, 
                                                           @Param("month") Integer month);
    
    /**
     * 获取日均交易量趋势
     * 
     * @param days 天数
     * @return 日均交易量趋势
     */
    @MapKey("date")
    List<Map<String, Object>> getDailyTransactionTrend(@Param("days") Integer days);
}
