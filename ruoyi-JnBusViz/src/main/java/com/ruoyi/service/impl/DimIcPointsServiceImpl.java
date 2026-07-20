package com.ruoyi.service.impl;

import com.ruoyi.domain.DimIcPoints;
import com.ruoyi.mapper.DimIcPointsMapper;
import com.ruoyi.service.DimIcPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * IC 卡服务网点 Service 实现类
 * 
 * @author 若依
 */
@Service
public class DimIcPointsServiceImpl implements DimIcPointsService {
    
    @Autowired
    private DimIcPointsMapper dimIcPointsMapper;
    
    @Override
    public List<DimIcPoints> selectDimIcPointsList(DimIcPoints dimIcPoints) {
        return dimIcPointsMapper.selectDimIcPointsList(dimIcPoints);
    }
    
    @Override
    public List<Map<String, Object>> getAllIcPointsBasicInfo() {
        return dimIcPointsMapper.getAllIcPointsBasicInfo();
    }
    
    @Override
    public List<Map<String, Object>> countPointsByType() {
        return dimIcPointsMapper.countPointsByType();
    }
    
    @Override
    public List<Map<String, Object>> getGisDisplayData() {
        return dimIcPointsMapper.getGisDisplayData();
    }
}
