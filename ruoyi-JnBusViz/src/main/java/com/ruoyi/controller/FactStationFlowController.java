package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.FactStationFlow;
import com.ruoyi.service.FactStationFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交站点客流统计 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/station/flow")
public class FactStationFlowController extends BaseController {
    
    @Autowired
    private FactStationFlowService factStationFlowService;
    
    /**
     * 查询站点客流列表
     */
    @GetMapping("/list")
    public AjaxResult list(FactStationFlow factStationFlow) {
        List<FactStationFlow> list = factStationFlowService.selectFactStationFlowList(factStationFlow);
        return success(list);
    }
    
    /**
     * 获取总客流量统计
     */
    @GetMapping("/totalFlow")
    public AjaxResult getTotalFlow(@RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer month) {
        Long totalFlow = factStationFlowService.getTotalFlow(year, month);
        return success(totalFlow);
    }
    
    /**
     * 按线路统计客流量
     */
    @GetMapping("/byLine")
    public AjaxResult getFlowByLine(@RequestParam(required = false) Integer year,
                                   @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> flowByLine = factStationFlowService.getFlowByLine(year, month);
        return success(flowByLine);
    }
    
    /**
     * 按站点统计客流量
     */
    @GetMapping("/byStation")
    public AjaxResult getFlowByStation(@RequestParam(required = false) Integer year,
                                      @RequestParam(required = false) Integer month,
                                      @RequestParam(defaultValue = "10") Integer limit) {
        List<Map<String, Object>> flowByStation = factStationFlowService.getFlowByStation(year, month, limit);
        return success(flowByStation);
    }
    
    /**
     * 按区域统计客流量
     */
    @GetMapping("/byZone")
    public AjaxResult getFlowByZone(@RequestParam(required = false) Integer year,
                                   @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> flowByZone = factStationFlowService.getFlowByZone(year, month);
        return success(flowByZone);
    }
    
    /**
     * 按时段统计客流量
     */
    @GetMapping("/byTimePeriod")
    public AjaxResult getFlowByTimePeriod(@RequestParam(required = false) Integer year,
                                         @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> flowByTimePeriod = factStationFlowService.getFlowByTimePeriod(year, month);
        return success(flowByTimePeriod);
    }
    
    /**
     * 获取客流趋势数据
     */
    @GetMapping("/trend")
    public AjaxResult getFlowTrend(@RequestParam(defaultValue = "30") Integer days) {
        List<Map<String, Object>> flowTrend = factStationFlowService.getFlowTrend(days);
        return success(flowTrend);
    }
    
    /**
     * 获取热门线路TOP10
     */
    @GetMapping("/topLines")
    public AjaxResult getTopLines(@RequestParam(required = false) Integer year,
                                 @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> topLines = factStationFlowService.getTopLines(year, month);
        return success(topLines);
    }
    
    /**
     * 获取热门站点TOP10
     */
    @GetMapping("/topStations")
    public AjaxResult getTopStations(@RequestParam(required = false) Integer year,
                                    @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> topStations = factStationFlowService.getTopStations(year, month);
        return success(topStations);
    }
    
    /**
     * 获取平均满载率
     */
    @GetMapping("/avgLoadFactor")
    public AjaxResult getAverageLoadFactor(@RequestParam(required = false) Integer year,
                                          @RequestParam(required = false) Integer month) {
        Double avgLoadFactor = factStationFlowService.getAverageLoadFactor(year, month);
        return success(avgLoadFactor);
    }
    
    /**
     * 获取同比变化率
     */
    @GetMapping("/yoyRate")
    public AjaxResult getYoyRate(@RequestParam(required = false) Integer year,
                                @RequestParam(required = false) Integer month) {
        Double yoyRate = factStationFlowService.getYoyRate(year, month);
        return success(yoyRate);
    }
    
    /**
     * 获取实时客流数据
     */
    @GetMapping("/realTime")
    public AjaxResult getRealTimeFlow(@RequestParam(defaultValue = "24") Integer hours) {
        List<Map<String, Object>> realTimeFlow = factStationFlowService.getRealTimeFlow(hours);
        return success(realTimeFlow);
    }
    
    /**
     * 获取客流高峰时段
     */
    @GetMapping("/peakHours")
    public AjaxResult getPeakHours(@RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> peakHours = factStationFlowService.getPeakHours(year, month);
        return success(peakHours);
    }
    
    /**
     * 获取各线路满载率排名
     */
    @GetMapping("/loadFactorRanking")
    public AjaxResult getLoadFactorRanking(@RequestParam(required = false) Integer year,
                                          @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> loadFactorRanking = factStationFlowService.getLoadFactorRanking(year, month);
        return success(loadFactorRanking);
    }
    
    /**
     * 获取大屏综合统计数据
     */
    @GetMapping("/dashboard")
    public AjaxResult getDashboardStats(@RequestParam(required = false) Integer year,
                                       @RequestParam(required = false) Integer month) {
        Map<String, Object> dashboardStats = factStationFlowService.getDashboardStats(year, month);
        return success(dashboardStats);
    }
}
