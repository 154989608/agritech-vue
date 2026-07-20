package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.DimIcPoints;
import com.ruoyi.service.DimIcPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * IC 卡服务网点 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/icpoints")
public class DimIcPointsController extends BaseController {
    
    @Autowired
    private DimIcPointsService dimIcPointsService;
    
    /**
     * 查询网点列表
     */
    @GetMapping("/list")
    public AjaxResult list(DimIcPoints dimIcPoints) {
        List<DimIcPoints> list = dimIcPointsService.selectDimIcPointsList(dimIcPoints);
        return success(list);
    }
    
    /**
     * 获取所有网点基本信息
     */
    @GetMapping("/all")
    public AjaxResult getAllIcPointsBasicInfo() {
        List<Map<String, Object>> points = dimIcPointsService.getAllIcPointsBasicInfo();
        return success(points);
    }
    
    /**
     * 按类型统计网点数量
     */
    @GetMapping("/count/byType")
    public AjaxResult countPointsByType() {
        List<Map<String, Object>> result = dimIcPointsService.countPointsByType();
        return success(result);
    }
    
    /**
     * 获取 GIS 地图展示数据
     */
    @GetMapping("/gis/data")
    public AjaxResult getGisDisplayData() {
        List<Map<String, Object>> result = dimIcPointsService.getGisDisplayData();
        return success(result);
    }
}
