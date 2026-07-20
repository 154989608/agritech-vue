package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.DimStops;
import com.ruoyi.service.DimStopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交站点维度 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/stops")
public class DimStopsController extends BaseController {
    
    @Autowired
    private DimStopsService dimStopsService;
    
    /**
     * 查询站点列表
     */
    @GetMapping("/list")
    public AjaxResult list(DimStops dimStops) {
        List<DimStops> list = dimStopsService.selectDimStopsList(dimStops);
        return success(list);
    }
    
    /**
     * 获取所有站点（用于 GIS 地图）
     */
    @GetMapping("/map/all")
    public AjaxResult getAllStopsForMap() {
        List<Map<String, Object>> stops = dimStopsService.getAllStopsForMap();
        return success(stops);
    }
    
    /**
     * 按区域类型统计站点数量
     */
    @GetMapping("/zoneType/count")
    public AjaxResult countStopsByZoneType() {
        List<Map<String, Object>> result = dimStopsService.countStopsByZoneType();
        return success(result);
    }
    
    /**
     * 获取站点经纬度信息
     */
    @GetMapping("/location")
    public AjaxResult getStopLocation(@RequestParam String stopName) {
        Map<String, Object> location = dimStopsService.getStopLocation(stopName);
        return success(location);
    }
    
    /**
     * 获取指定区域的站点
     */
    @GetMapping("/byZoneType")
    public AjaxResult getStopsByZoneType(@RequestParam String zoneType) {
        List<DimStops> stops = dimStopsService.getStopsByZoneType(zoneType);
        return success(stops);
    }
}
