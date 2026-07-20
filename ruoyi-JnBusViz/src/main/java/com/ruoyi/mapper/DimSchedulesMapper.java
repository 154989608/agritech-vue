package com.ruoyi.mapper;

import com.ruoyi.domain.DimSchedules;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 公交线路班次 Mapper 接口
 * 
 * @author 若依
 */
public interface DimSchedulesMapper {
    
    /**
     * 查询班次列表
     * 
     * @param dimSchedules 班次信息
     * @return 班次集合
     */
    List<DimSchedules> selectDimSchedulesList(DimSchedules dimSchedules);
    
    /**
     * 获取所有线路基本信息
     * 
     * @return 线路集合
     */
    @MapKey("scheduleNum")
    List<Map<String, Object>> getAllSchedulesBasicInfo();
    
    /**
     * 按线路统计班次数量
     * 
     * @param lineName 线路名称
     * @param date 日期
     * @return 班次统计
     */
    @MapKey("lineName")
    List<Map<String, Object>> countSchedulesByLine(@Param("lineName") String lineName, 
                                                          @Param("date") String date);
    
    /**
     * 获取班次作废率统计
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 班次作废率统计
     */
    @MapKey("date")
    List<Map<String, Object>> getScheduleCancelRate(@Param("startDate") String startDate, 
                                                           @Param("endDate") String endDate);
    
    /**
     * 获取线路客流与班次匹配度
     * 
     * @param lineName 线路名称
     * @param date 日期
     * @return 匹配度数据
     */
    @MapKey("hour")
    List<Map<String, Object>> getScheduleMatchAnalysis(@Param("lineName") String lineName, 
                                                              @Param("date") String date);
    
    /**
     * 获取运力缺口预警
     * 
     * @param date 日期
     * @return 运力缺口数据
     */
    @MapKey("lineName")
    List<Map<String, Object>> getCapacityShortageWarning(@Param("date") String date);
}
