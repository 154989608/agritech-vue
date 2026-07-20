package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.DimRouteStationDetails;
import com.ruoyi.service.DimRouteStationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交线路站点详情 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/route/stations")
public class DimRouteStationDetailsController extends BaseController {
    
    @Autowired
    private DimRouteStationDetailsService dimRouteStationDetailsService;
    
    /**
     * 查询站点详情列表
     */
    @GetMapping("/list")
    public AjaxResult list(DimRouteStationDetails dimRouteStationDetails) {
        List<DimRouteStationDetails> list = dimRouteStationDetailsService.selectDimRouteStationDetailsList(dimRouteStationDetails);
        return success(list);
    }
    
    /**
     * 获取线路站点详情（含顺序）
     */
    @GetMapping("/detail/byLine")
    public AjaxResult getLineStationsDetail(@RequestParam String lineName) {
        List<Map<String, Object>> result = dimRouteStationDetailsService.getLineStationsDetail(lineName);
        return success(result);
    }
    
    /**
     * 计算站点间距
     */
    @GetMapping("/distance/byLine")
    public AjaxResult calculateStationDistance(@RequestParam String lineName) {
        List<Map<String, Object>> result = dimRouteStationDetailsService.calculateStationDistance(lineName);
        return success(result);
    }
    
    /**
     * 获取首末班车匹配度分析
     */
    @GetMapping("/match/analysis")
    public AjaxResult getFirstLastBusMatchAnalysis(@RequestParam String lineName,
                                                   @RequestParam String date) {
        List<Map<String, Object>> result = dimRouteStationDetailsService.getFirstLastBusMatchAnalysis(lineName, date);
        return success(result);
    }
    
    /**
     * 获取站点覆盖率分析
     */
    @GetMapping("/coverage")
    public AjaxResult getStationCoverageAnalysis(@RequestParam String stopName) {
        Map<String, Object> result = dimRouteStationDetailsService.getStationCoverageAnalysis(stopName);
        return success(result);
    }
}
