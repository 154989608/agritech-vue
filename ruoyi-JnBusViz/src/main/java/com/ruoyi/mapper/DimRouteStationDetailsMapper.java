package com.ruoyi.mapper;

import com.ruoyi.domain.DimRouteStationDetails;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 公交线路站点详情 Mapper 接口
 * 
 * @author 若依
 */
public interface DimRouteStationDetailsMapper {
    
    /**
     * 查询站点详情列表
     * 
     * @param dimRouteStationDetails 站点详情信息
     * @return 站点详情集合
     */
    List<DimRouteStationDetails> selectDimRouteStationDetailsList(DimRouteStationDetails dimRouteStationDetails);
    
    /**
     * 获取线路站点详情（含顺序）
     * 
     * @param lineName 线路名称
     * @return 站点详情
     */
    @MapKey("stopOrder")
    List<Map<String, Object>> getLineStationsDetail(@Param("lineName") String lineName);
    
    /**
     * 计算站点间距
     * 
     * @param lineName 线路名称
     * @return 站点间距数据
     */
    @MapKey("stopOrder")
    List<Map<String, Object>> calculateStationDistance(@Param("lineName") String lineName);
    
    /**
     * 获取首末班车匹配度分析
     * 
     * @param lineName 线路名称
     * @param date 日期
     * @return 匹配度数据
     */
    @MapKey("stopName")
    List<Map<String, Object>> getFirstLastBusMatchAnalysis(@Param("lineName") String lineName, 
                                                                  @Param("date") String date);
    
    /**
     * 获取站点覆盖率分析
     * 
     * @param stopName 站点名称
     * @return 覆盖率数据
     */
    @MapKey("stopName")
    Map<String, Object> getStationCoverageAnalysis(@Param("stopName") String stopName);
}
