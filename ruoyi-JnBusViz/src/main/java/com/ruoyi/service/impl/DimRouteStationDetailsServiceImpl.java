package com.ruoyi.service.impl;

import com.ruoyi.domain.DimRouteStationDetails;
import com.ruoyi.mapper.DimRouteStationDetailsMapper;
import com.ruoyi.service.DimRouteStationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 公交线路站点详情 Service 实现类
 * 
 * @author 若依
 */
@Service
public class DimRouteStationDetailsServiceImpl implements DimRouteStationDetailsService {
    
    @Autowired
    private DimRouteStationDetailsMapper dimRouteStationDetailsMapper;
    
    @Override
    public List<DimRouteStationDetails> selectDimRouteStationDetailsList(DimRouteStationDetails dimRouteStationDetails) {
        return dimRouteStationDetailsMapper.selectDimRouteStationDetailsList(dimRouteStationDetails);
    }
    
    @Override
    public List<Map<String, Object>> getLineStationsDetail(String lineName) {
        return dimRouteStationDetailsMapper.getLineStationsDetail(lineName);
    }
    
    @Override
    public List<Map<String, Object>> calculateStationDistance(String lineName) {
        return dimRouteStationDetailsMapper.calculateStationDistance(lineName);
    }
    
    @Override
    public List<Map<String, Object>> getFirstLastBusMatchAnalysis(String lineName, String date) {
        return dimRouteStationDetailsMapper.getFirstLastBusMatchAnalysis(lineName, date);
    }
    
    @Override
    public Map<String, Object> getStationCoverageAnalysis(String stopName) {
        return dimRouteStationDetailsMapper.getStationCoverageAnalysis(stopName);
    }
}
