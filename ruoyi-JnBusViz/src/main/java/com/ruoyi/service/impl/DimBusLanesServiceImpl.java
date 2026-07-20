package com.ruoyi.service.impl;

import com.ruoyi.domain.DimBusLanes;
import com.ruoyi.mapper.DimBusLanesMapper;
import com.ruoyi.service.DimBusLanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 公交专用道 Service 实现类
 * 
 * @author 若依
 */
@Service
public class DimBusLanesServiceImpl implements DimBusLanesService {
    
    @Autowired
    private DimBusLanesMapper dimBusLanesMapper;
    
    @Override
    public List<DimBusLanes> selectDimBusLanesList(DimBusLanes dimBusLanes) {
        return dimBusLanesMapper.selectDimBusLanesList(dimBusLanes);
    }
    
    @Override
    public List<Map<String, Object>> getAllBusLanesBasicInfo() {
        return dimBusLanesMapper.getAllBusLanesBasicInfo();
    }
    
    @Override
    public List<Map<String, Object>> countLengthByType() {
        return dimBusLanesMapper.countLengthByType();
    }
    
    @Override
    public Map<String, Object> getLineCoverage(String lineName) {
        return dimBusLanesMapper.getLineCoverage(lineName);
    }
    
    @Override
    public List<Map<String, Object>> getGisDisplayData() {
        return dimBusLanesMapper.getGisDisplayData();
    }
}
