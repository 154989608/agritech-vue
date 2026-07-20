package com.ruoyi.service.impl;

import com.ruoyi.domain.DimSchedules;
import com.ruoyi.mapper.DimSchedulesMapper;
import com.ruoyi.service.DimSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 公交线路班次 Service 实现类
 * 
 * @author 若依
 */
@Service
public class DimSchedulesServiceImpl implements DimSchedulesService {
    
    @Autowired
    private DimSchedulesMapper dimSchedulesMapper;
    
    @Override
    public List<DimSchedules> selectDimSchedulesList(DimSchedules dimSchedules) {
        return dimSchedulesMapper.selectDimSchedulesList(dimSchedules);
    }
    
    @Override
    public List<Map<String, Object>> getAllSchedulesBasicInfo() {
        return dimSchedulesMapper.getAllSchedulesBasicInfo();
    }
    
    @Override
    public List<Map<String, Object>> countSchedulesByLine(String lineName, String date) {
        return dimSchedulesMapper.countSchedulesByLine(lineName, date);
    }
    
    @Override
    public List<Map<String, Object>> getScheduleCancelRate(String startDate, String endDate) {
        return dimSchedulesMapper.getScheduleCancelRate(startDate, endDate);
    }
    
    @Override
    public List<Map<String, Object>> getScheduleMatchAnalysis(String lineName, String date) {
        return dimSchedulesMapper.getScheduleMatchAnalysis(lineName, date);
    }
    
    @Override
    public List<Map<String, Object>> getCapacityShortageWarning(String date) {
        return dimSchedulesMapper.getCapacityShortageWarning(date);
    }
}
