package com.ruoyi.service.impl;

import com.ruoyi.domain.DimStops;
import com.ruoyi.mapper.DimStopsMapper;
import com.ruoyi.service.DimStopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 公交站点维度 Service 实现类
 * 
 * @author 若依
 */
@Service
public class DimStopsServiceImpl implements DimStopsService {
    
    @Autowired
    private DimStopsMapper dimStopsMapper;
    
    @Override
    public List<DimStops> selectDimStopsList(DimStops dimStops) {
        return dimStopsMapper.selectDimStopsList(dimStops);
    }
    
    @Override
    public List<Map<String, Object>> getAllStopsForMap() {
        return dimStopsMapper.getAllStopsForMap();
    }
    
    @Override
    public List<Map<String, Object>> countStopsByZoneType() {
        return dimStopsMapper.countStopsByZoneType();
    }
    
    @Override
    public Map<String, Object> getStopLocation(String stopName) {
        return dimStopsMapper.getStopLocation(stopName);
    }
    
    @Override
    public List<DimStops> getStopsByZoneType(String zoneType) {
        return dimStopsMapper.getStopsByZoneType(zoneType);
    }
}
