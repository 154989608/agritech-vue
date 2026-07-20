package com.ruoyi.service.impl;

import com.ruoyi.domain.DimLines;
import com.ruoyi.mapper.DimLinesMapper;
import com.ruoyi.service.DimLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 公交线路维度 Service 实现类
 * 
 * @author 若依
 */
@Service
public class DimLinesServiceImpl implements DimLinesService {
    
    @Autowired
    private DimLinesMapper dimLinesMapper;
    
    @Override
    public List<DimLines> selectDimLinesList(DimLines dimLines) {
        return dimLinesMapper.selectDimLinesList(dimLines);
    }
    
    @Override
    public List<Map<String, Object>> getAllLinesBasicInfo() {
        return dimLinesMapper.getAllLinesBasicInfo();
    }
    
    @Override
    public List<Map<String, Object>> countLinesByType() {
        return dimLinesMapper.countLinesByType();
    }
    
    @Override
    public Map<String, Object> getLineDetail(String lineName) {
        return dimLinesMapper.getLineDetail(lineName);
    }
}
