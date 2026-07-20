package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.DimBusLanes;
import com.ruoyi.service.DimBusLanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交专用道 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/buslanes")
public class DimBusLanesController extends BaseController {
    
    @Autowired
    private DimBusLanesService dimBusLanesService;
    
    /**
     * 查询专用道列表
     */
    @GetMapping("/list")
    public AjaxResult list(DimBusLanes dimBusLanes) {
        List<DimBusLanes> list = dimBusLanesService.selectDimBusLanesList(dimBusLanes);
        return success(list);
    }
    
    /**
     * 获取所有专用道基本信息
     */
    @GetMapping("/all")
    public AjaxResult getAllBusLanesBasicInfo() {
        List<Map<String, Object>> lanes = dimBusLanesService.getAllBusLanesBasicInfo();
        return success(lanes);
    }
    
    /**
     * 按类型统计专用道长度
     */
    @GetMapping("/count/byType")
    public AjaxResult countLengthByType() {
        List<Map<String, Object>> result = dimBusLanesService.countLengthByType();
        return success(result);
    }
    
    /**
     * 获取线路专用道覆盖情况
     */
    @GetMapping("/coverage/byLine")
    public AjaxResult getLineCoverage(@RequestParam String lineName) {
        Map<String, Object> result = dimBusLanesService.getLineCoverage(lineName);
        return success(result);
    }
    
    /**
     * 获取 GIS 地图展示数据
     */
    @GetMapping("/gis/data")
    public AjaxResult getGisDisplayData() {
        List<Map<String, Object>> result = dimBusLanesService.getGisDisplayData();
        return success(result);
    }
}
