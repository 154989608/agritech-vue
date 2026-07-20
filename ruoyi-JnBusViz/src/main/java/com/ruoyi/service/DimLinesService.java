package com.ruoyi.service;

import com.ruoyi.domain.DimLines;
import java.util.List;
import java.util.Map;

/**
 * 公交线路维度 Service 接口
 * 
 * @author 若依
 */
public interface DimLinesService {
    
    /**
     * 查询线路列表
     * 
     * @param dimLines 线路信息
     * @return 线路集合
     */
    public List<DimLines> selectDimLinesList(DimLines dimLines);
    
    /**
     * 获取所有线路基本信息
     * 
     * @return 线路集合
     */
    public List<Map<String, Object>> getAllLinesBasicInfo();
    
    /**
     * 按线路类型统计
     * 
     * @return 线路类型统计
     */
    public List<Map<String, Object>> countLinesByType();
    
    /**
     * 获取线路详细信息（含站点数）
     * 
     * @param lineName 线路名称
     * @return 线路详细信息
     */
    public Map<String, Object> getLineDetail(String lineName);
}
