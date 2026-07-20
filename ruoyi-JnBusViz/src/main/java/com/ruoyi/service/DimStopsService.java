package com.ruoyi.service;

import com.ruoyi.domain.DimStops;
import java.util.List;
import java.util.Map;

/**
 * 公交站点维度 Service 接口
 * 
 * @author 若依
 */
public interface DimStopsService {
    
    /**
     * 查询站点列表
     * 
     * @param dimStops 站点信息
     * @return 站点集合
     */
    public List<DimStops> selectDimStopsList(DimStops dimStops);
    
    /**
     * 获取所有站点（用于 GIS 地图）
     * 
     * @return 站点集合
     */
    public List<Map<String, Object>> getAllStopsForMap();
    
    /**
     * 按区域类型统计站点数量
     * 
     * @return 区域类型统计
     */
    public List<Map<String, Object>> countStopsByZoneType();
    
    /**
     * 获取站点经纬度信息
     * 
     * @param stopName 站点名称
     * @return 站点经纬度
     */
    public Map<String, Object> getStopLocation(String stopName);
    
    /**
     * 获取指定区域的站点
     * 
     * @param zoneType 区域类型
     * @return 站点集合
     */
    public List<DimStops> getStopsByZoneType(String zoneType);
}
