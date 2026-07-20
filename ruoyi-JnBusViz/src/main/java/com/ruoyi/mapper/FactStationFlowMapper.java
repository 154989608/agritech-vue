package com.ruoyi.mapper;

import com.ruoyi.domain.FactStationFlow;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 公交站点客流统计 Mapper 接口
 * 
 * @author 若依
 */
public interface FactStationFlowMapper {
    
    /**
     * 查询站点客流列表
     * 
     * @param factStationFlow 站点客流信息
     * @return 站点客流集合
     */
    List<FactStationFlow> selectFactStationFlowList(FactStationFlow factStationFlow);
    
    /**
     * 获取总客流量统计
     * 
     * @param year 年份
     * @param month 月份
     * @return 总客流量
     */
    Long getTotalFlow(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 按线路统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 线路客流量统计
     */
    @MapKey("lineName")
    List<Map<String, Object>> getFlowByLine(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 按站点统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @param limit 限制数量
     * @return 站点客流量统计
     */
    @MapKey("stopName")
    List<Map<String, Object>> getFlowByStation(@Param("year") Integer year, @Param("month") Integer month, @Param("limit") Integer limit);
    
    /**
     * 按区域统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 区域客流量统计
     */
    @MapKey("zoneType")
    List<Map<String, Object>> getFlowByZone(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 按时段统计客流量
     * 
     * @param year 年份
     * @param month 月份
     * @return 时段客流量统计
     */
    @MapKey("timePeriod")
    List<Map<String, Object>> getFlowByTimePeriod(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取客流趋势数据
     * 
     * @param days 天数
     * @return 趋势数据
     */
    @MapKey("date")
    List<Map<String, Object>> getFlowTrend(@Param("days") Integer days);
    
    /**
     * 获取热门线路 TOP10
     * 
     * @param year 年份
     * @param month 月份
     * @return 热门线路
     */
    @MapKey("lineName")
    List<Map<String, Object>> getTopLines(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取热门站点 TOP10
     * 
     * @param year 年份
     * @param month 月份
     * @return 热门站点
     */
    @MapKey("stopName")
    List<Map<String, Object>> getTopStations(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取平均满载率
     * 
     * @param year 年份
     * @param month 月份
     * @return 平均满载率
     */
    Double getAverageLoadFactor(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取同比变化率
     * 
     * @param year 年份
     * @param month 月份
     * @return 同比变化率
     */
    Double getYoyRate(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取实时客流数据
     * 
     * @param hours 小时数
     * @return 实时客流数据
     */
    @MapKey("hour")
    List<Map<String, Object>> getRealTimeFlow(@Param("hours") Integer hours);
    
    /**
     * 获取客流高峰时段
     * 
     * @param year 年份
     * @param month 月份
     * @return 高峰时段
     */
    @MapKey("peakHour")
    List<Map<String, Object>> getPeakHours(@Param("year") Integer year, @Param("month") Integer month);
    
    /**
     * 获取各线路满载率排名
     * 
     * @param year 年份
     * @param month 月份
     * @return 满载率排名
     */
    @MapKey("lineName")
    List<Map<String, Object>> getLoadFactorRanking(@Param("year") Integer year, @Param("month") Integer month);
}
