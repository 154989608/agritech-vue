package com.ruoyi.service;

import com.ruoyi.domain.FactStationFlow;
import java.util.List;
import java.util.Map;

/**
 * 公交站点客流统计 Service接口
 * 
 * @author 若依
 */
public interface FactStationFlowService {
    
    /**
     * 查询站点客流列表
     * 
     * @param factStationFlow 站点客流信息
     * @return 站点客流集合
     */
    public List<FactStationFlow> selectFactStationFlowList(FactStationFlow factStationFlow);
    
    /**
     * 获取总客流量统计
     * 
     * @param year 年份
     * @param month 月份
     * @return 总客流量
     */
    public Long getTotalFlow(Integer year, Integer month);
    
    /**
     * 按线路统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 线路客流量统计
     */
    public List<Map<String, Object>> getFlowByLine(Integer year, Integer month);
    
    /**
     * 按站点统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @param limit 限制数量
     * @return 站点客流量统计
     */
    public List<Map<String, Object>> getFlowByStation(Integer year, Integer month, Integer limit);
    
    /**
     * 按区域统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 区域客流量统计
     */
    public List<Map<String, Object>> getFlowByZone(Integer year, Integer month);
    
    /**
     * 按时段统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 时段客流量统计
     */
    public List<Map<String, Object>> getFlowByTimePeriod(Integer year, Integer month);
    
    /**
     * 获取客流趋势数据
     * 
     * @param days 天数
     * @return 趋势数据
     */
    public List<Map<String, Object>> getFlowTrend(Integer days);
    
    /**
     * 获取热门线路TOP10
     * 
     * @param year 年份
     * @param month 月份
     * @return 热门线路
     */
    public List<Map<String, Object>> getTopLines(Integer year, Integer month);
    
    /**
     * 获取热门站点TOP10
     * 
     * @param year 年份
     * @param month 月份
     * @return 热门站点
     */
    public List<Map<String, Object>> getTopStations(Integer year, Integer month);
    
    /**
     * 获取平均满载率
     * 
     * @param year 年份
     * @param month 月份
     * @return 平均满载率
     */
    public Double getAverageLoadFactor(Integer year, Integer month);
    
    /**
     * 获取同比变化率
     * 
     * @param year 年份
     * @param month 月份
     * @return 同比变化率
     */
    public Double getYoyRate(Integer year, Integer month);
    
    /**
     * 获取实时客流数据
     * 
     * @param hours 小时数
     * @return 实时客流数据
     */
    public List<Map<String, Object>> getRealTimeFlow(Integer hours);
    
    /**
     * 获取客流高峰时段
     * 
     * @param year 年份
     * @param month 月份
     * @return 高峰时段
     */
    public List<Map<String, Object>> getPeakHours(Integer year, Integer month);
    
    /**
     * 获取各线路满载率排名
     * 
     * @param year 年份
     * @param month 月份
     * @return 满载率排名
     */
    public List<Map<String, Object>> getLoadFactorRanking(Integer year, Integer month);
    
    /**
     * 获取综合统计数据（用于大屏展示）
     * 
     * @param year 年份
     * @param month 月份
     * @return 综合统计数据
     */
    public Map<String, Object> getDashboardStats(Integer year, Integer month);
}
