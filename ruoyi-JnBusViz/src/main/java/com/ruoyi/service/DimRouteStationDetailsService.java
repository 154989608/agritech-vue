package com.ruoyi.service;

import com.ruoyi.domain.DimRouteStationDetails;
import java.util.List;
import java.util.Map;

/**
 * 公交线路站点详情 Service 接口
 * 
 * @author 若依
 */
public interface DimRouteStationDetailsService {
    
    /**
     * 查询站点详情列表
     * 
     * @param dimRouteStationDetails 站点详情信息
     * @return 站点详情集合
     */
    public List<DimRouteStationDetails> selectDimRouteStationDetailsList(DimRouteStationDetails dimRouteStationDetails);
    
    /**
     * 获取线路站点详情（含顺序）
     * 
     * @param lineName 线路名称
     * @return 站点详情
     */
    public List<Map<String, Object>> getLineStationsDetail(String lineName);
    
    /**
     * 计算站点间距
     * 
     * @param lineName 线路名称
     * @return 站点间距数据
     */
    public List<Map<String, Object>> calculateStationDistance(String lineName);
    
    /**
     * 获取首末班车匹配度分析
     * 
     * @param lineName 线路名称
     * @param date 日期
     * @return 匹配度数据
     */
    public List<Map<String, Object>> getFirstLastBusMatchAnalysis(String lineName, String date);
    
    /**
     * 获取站点覆盖率分析
     * 
     * @param stopName 站点名称
     * @return 覆盖率数据
     */
    public Map<String, Object> getStationCoverageAnalysis(String stopName);
}
