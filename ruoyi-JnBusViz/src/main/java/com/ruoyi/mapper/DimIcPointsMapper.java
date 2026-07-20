package com.ruoyi.mapper;

import com.ruoyi.domain.DimIcPoints;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * IC 卡服务网点 Mapper 接口
 * 
 * @author 若依
 */
public interface DimIcPointsMapper {
    
    /**
     * 查询网点列表
     * 
     * @param dimIcPoints 网点信息
     * @return 网点集合
     */
    List<DimIcPoints> selectDimIcPointsList(DimIcPoints dimIcPoints);
    
    /**
     * 获取所有网点基本信息
     * 
     * @return 网点集合
     */
    @MapKey("pointName")
    List<Map<String, Object>> getAllIcPointsBasicInfo();
    
    /**
     * 按类型统计网点数量
     * 
     * @return 类型统计
     */
    @MapKey("pointType")
    List<Map<String, Object>> countPointsByType();
    
    /**
     * 获取 GIS 地图展示数据
     * 
     * @return GIS 数据
     */
    @MapKey("pointName")
    List<Map<String, Object>> getGisDisplayData();
}
