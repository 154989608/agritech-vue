package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.DimSchedules;
import com.ruoyi.service.DimSchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交线路班次 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/schedules")
public class DimSchedulesController extends BaseController {
    
    @Autowired
    private DimSchedulesService dimSchedulesService;
    
    /**
     * 查询班次列表
     */
    @GetMapping("/list")
    public AjaxResult list(DimSchedules dimSchedules) {
        List<DimSchedules> list = dimSchedulesService.selectDimSchedulesList(dimSchedules);
        return success(list);
    }
    
    /**
     * 获取所有线路基本信息
     */
    @GetMapping("/all")
    public AjaxResult getAllSchedulesBasicInfo() {
        List<Map<String, Object>> schedules = dimSchedulesService.getAllSchedulesBasicInfo();
        return success(schedules);
    }
    
    /**
     * 按线路统计班次数量
     */
    @GetMapping("/count/byLine")
    public AjaxResult countSchedulesByLine(@RequestParam(required = false) String lineName,
                                           @RequestParam(required = false) String date) {
        List<Map<String, Object>> result = dimSchedulesService.countSchedulesByLine(lineName, date);
        return success(result);
    }
    
    /**
     * 获取班次作废率统计
     */
    @GetMapping("/cancelRate")
    public AjaxResult getScheduleCancelRate(@RequestParam String startDate,
                                            @RequestParam String endDate) {
        List<Map<String, Object>> result = dimSchedulesService.getScheduleCancelRate(startDate, endDate);
        return success(result);
    }
    
    /**
     * 获取线路客流与班次匹配度
     */
    @GetMapping("/match/analysis")
    public AjaxResult getScheduleMatchAnalysis(@RequestParam String lineName,
                                               @RequestParam String date) {
        List<Map<String, Object>> result = dimSchedulesService.getScheduleMatchAnalysis(lineName, date);
        return success(result);
    }
    
    /**
     * 获取运力缺口预警
     */
    @GetMapping("/capacity/shortage")
    public AjaxResult getCapacityShortageWarning(@RequestParam String date) {
        List<Map<String, Object>> result = dimSchedulesService.getCapacityShortageWarning(date);
        return success(result);
    }
}
