package com.ruoyi.mapper;

import com.ruoyi.domain.DimBusLanes;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 公交专用道 Mapper 接口
 * 
 * @author 若依
 */
public interface DimBusLanesMapper {
    
    /**
     * 查询专用道列表
     * 
     * @param dimBusLanes 专用道信息
     * @return 专用道集合
     */
    List<DimBusLanes> selectDimBusLanesList(DimBusLanes dimBusLanes);
    
    /**
     * 获取所有专用道基本信息
     * 
     * @return 专用道集合
     */
    @MapKey("roadName")
    List<Map<String, Object>> getAllBusLanesBasicInfo();
    
    /**
     * 按类型统计专用道长度
     * 
     * @return 类型统计
     */
    @MapKey("laneType")
    List<Map<String, Object>> countLengthByType();
    
    /**
     * 获取线路专用道覆盖情况
     * 
     * @param lineName 线路名称
     * @return 覆盖情况
     */
    @MapKey("lineName")
    Map<String, Object> getLineCoverage(@Param("lineName") String lineName);
    
    /**
     * 获取 GIS 地图展示数据
     * 
     * @return GIS 数据
     */
    @MapKey("roadName")
    List<Map<String, Object>> getGisDisplayData();
}
