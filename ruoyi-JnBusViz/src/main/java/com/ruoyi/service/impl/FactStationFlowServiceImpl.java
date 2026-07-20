package com.ruoyi.service.impl;

import com.ruoyi.domain.FactStationFlow;
import com.ruoyi.mapper.FactStationFlowMapper;
import com.ruoyi.service.FactStationFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公交站点客流统计 Service实现类
 * 
 * @author 若依
 */
@Service
public class FactStationFlowServiceImpl implements FactStationFlowService {
    
    @Autowired
    private FactStationFlowMapper factStationFlowMapper;
    
    @Override
    public List<FactStationFlow> selectFactStationFlowList(FactStationFlow factStationFlow) {
        return factStationFlowMapper.selectFactStationFlowList(factStationFlow);
    }
    
    @Override
    public Long getTotalFlow(Integer year, Integer month) {
        return factStationFlowMapper.getTotalFlow(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getFlowByLine(Integer year, Integer month) {
        return factStationFlowMapper.getFlowByLine(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getFlowByStation(Integer year, Integer month, Integer limit) {
        return factStationFlowMapper.getFlowByStation(year, month, limit);
    }
    
    @Override
    public List<Map<String, Object>> getFlowByZone(Integer year, Integer month) {
        return factStationFlowMapper.getFlowByZone(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getFlowByTimePeriod(Integer year, Integer month) {
        return factStationFlowMapper.getFlowByTimePeriod(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getFlowTrend(Integer days) {
        return factStationFlowMapper.getFlowTrend(days);
    }
    
    @Override
    public List<Map<String, Object>> getTopLines(Integer year, Integer month) {
        return factStationFlowMapper.getTopLines(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getTopStations(Integer year, Integer month) {
        return factStationFlowMapper.getTopStations(year, month);
    }
    
    @Override
    public Double getAverageLoadFactor(Integer year, Integer month) {
        return factStationFlowMapper.getAverageLoadFactor(year, month);
    }
    
    @Override
    public Double getYoyRate(Integer year, Integer month) {
        return factStationFlowMapper.getYoyRate(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getRealTimeFlow(Integer hours) {
        return factStationFlowMapper.getRealTimeFlow(hours);
    }
    
    @Override
    public List<Map<String, Object>> getPeakHours(Integer year, Integer month) {
        return factStationFlowMapper.getPeakHours(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getLoadFactorRanking(Integer year, Integer month) {
        return factStationFlowMapper.getLoadFactorRanking(year, month);
    }
    
    @Override
    public Map<String, Object> getDashboardStats(Integer year, Integer month) {
        Map<String, Object> dashboardStats = new HashMap<>();
        
        // 基础统计数据
        dashboardStats.put("totalFlow", getTotalFlow(year, month));
        dashboardStats.put("averageLoadFactor", getAverageLoadFactor(year, month));
        dashboardStats.put("yoyRate", getYoyRate(year, month));
        
        // 各维度统计数据
        dashboardStats.put("flowByLine", getFlowByLine(year, month));
        dashboardStats.put("flowByZone", getFlowByZone(year, month));
        dashboardStats.put("flowByTimePeriod", getFlowByTimePeriod(year, month));
        dashboardStats.put("topLines", getTopLines(year, month));
        dashboardStats.put("topStations", getTopStations(year, month));
        dashboardStats.put("peakHours", getPeakHours(year, month));
        dashboardStats.put("loadFactorRanking", getLoadFactorRanking(year, month));
        
        // 趋势数据
        dashboardStats.put("flowTrend", getFlowTrend(30));
        dashboardStats.put("realTimeFlow", getRealTimeFlow(24));
        
        return dashboardStats;
    }
}
