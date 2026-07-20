package com.ruoyi.service;

import com.ruoyi.domain.DimSchedules;
import java.util.List;
import java.util.Map;

/**
 * 公交线路班次 Service 接口
 * 
 * @author 若依
 */
public interface DimSchedulesService {
    
    /**
     * 查询班次列表
     * 
     * @param dimSchedules 班次信息
     * @return 班次集合
     */
    public List<DimSchedules> selectDimSchedulesList(DimSchedules dimSchedules);
    
    /**
     * 获取所有线路基本信息
     * 
     * @return 线路集合
     */
    public List<Map<String, Object>> getAllSchedulesBasicInfo();
    
    /**
     * 按线路统计班次数量
     * 
     * @param lineName 线路名称
     * @param date 日期
     * @return 班次统计
     */
    public List<Map<String, Object>> countSchedulesByLine(String lineName, String date);
    
    /**
     * 获取班次作废率统计
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 班次作废率统计
     */
    public List<Map<String, Object>> getScheduleCancelRate(String startDate, String endDate);
    
    /**
     * 获取线路客流与班次匹配度
     * 
     * @param lineName 线路名称
     * @param date 日期
     * @return 匹配度数据
     */
    public List<Map<String, Object>> getScheduleMatchAnalysis(String lineName, String date);
    
    /**
     * 获取运力缺口预警
     * 
     * @param date 日期
     * @return 运力缺口数据
     */
    public List<Map<String, Object>> getCapacityShortageWarning(String date);
}
