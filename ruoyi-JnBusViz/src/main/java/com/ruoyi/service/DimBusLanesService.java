package com.ruoyi.service;

import com.ruoyi.domain.DimBusLanes;
import java.util.List;
import java.util.Map;

/**
 * 公交专用道 Service 接口
 * 
 * @author 若依
 */
public interface DimBusLanesService {
    
    /**
     * 查询专用道列表
     * 
     * @param dimBusLanes 专用道信息
     * @return 专用道集合
     */
    public List<DimBusLanes> selectDimBusLanesList(DimBusLanes dimBusLanes);
    
    /**
     * 获取所有专用道基本信息
     * 
     * @return 专用道集合
     */
    public List<Map<String, Object>> getAllBusLanesBasicInfo();
    
    /**
     * 按类型统计专用道长度
     * 
     * @return 类型统计
     */
    public List<Map<String, Object>> countLengthByType();
    
    /**
     * 获取线路专用道覆盖情况
     * 
     * @param lineName 线路名称
     * @return 覆盖情况
     */
    public Map<String, Object> getLineCoverage(String lineName);
    
    /**
     * 获取 GIS 地图展示数据
     * 
     * @return GIS 数据
     */
    public List<Map<String, Object>> getGisDisplayData();
}
