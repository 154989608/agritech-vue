package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公交数据可视化大屏 - 综合测试 Controller
 *
 * @author 周帅涛
 */
@RestController
@RequestMapping("/bus/test")
public class BusVizTestController extends BaseController {

    @Autowired
    private FactStationFlowService factStationFlowService;

    @Autowired
    private DimStopsService dimStopsService;

    @Autowired
    private FactCardTransactionsService factCardTransactionsService;

    @Autowired
    private DimLinesService dimLinesService;

    @Autowired
    private DimSchedulesService dimSchedulesService;

    @Autowired
    private DimRouteStationDetailsService dimRouteStationDetailsService;

    @Autowired
    private DimBusLanesService dimBusLanesService;

    @Autowired
    private DimIcPointsService dimIcPointsService;

    /**
     * 获取大屏综合统计数据（用于测试）
     */
    @GetMapping("/dashboard")
    public AjaxResult getDashboardStats(@RequestParam(required = false) Integer year,
                                        @RequestParam(required = false) Integer month) {
        Map<String, Object> result = new HashMap<>();

        // 1. 客流统计数据
        result.put("stationFlow", factStationFlowService.getDashboardStats(year, month));

        // 2. 站点分布数据
        result.put("stopsDist", dimStopsService.countStopsByZoneType());

        // 3. IC 卡交易数据
        Map<String, Object> cardData = new HashMap<>();
        cardData.put("cardTypeDist", factCardTransactionsService.getFlowByCardType(year, month));
        cardData.put("transferTop10", factCardTransactionsService.getTopTransferStations(10));
        cardData.put("dailyTrend", factCardTransactionsService.getDailyTransactionTrend(30));
        result.put("cardTransactions", cardData);

        // 4. 线路数据
        Map<String, Object> lineData = new HashMap<>();
        lineData.put("allLines", dimLinesService.getAllLinesBasicInfo());
        result.put("lines", lineData);

        // 5. 班次数据
        Map<String, Object> scheduleData = new HashMap<>();
        scheduleData.put("allSchedules", dimSchedulesService.getAllSchedulesBasicInfo());
        scheduleData.put("todayStats", dimSchedulesService.countSchedulesByLine(null, java.time.LocalDate.now().toString()));
        result.put("schedules", scheduleData);

        // 6. 路线站点详情
        Map<String, Object> routeStationData = new HashMap<>();
        routeStationData.put("coverageAnalysis", dimRouteStationDetailsService.getStationCoverageAnalysis("火车站"));
        result.put("routeStations", routeStationData);

        // 7. 公交专用道
        Map<String, Object> busLaneData = new HashMap<>();
        busLaneData.put("typeDist", dimBusLanesService.countLengthByType());
        busLaneData.put("gisData", dimBusLanesService.getGisDisplayData());
        result.put("busLanes", busLaneData);

        // 8. IC 服务网点
        Map<String, Object> icPointData = new HashMap<>();
        icPointData.put("typeDist", dimIcPointsService.countPointsByType());
        icPointData.put("allPoints", dimIcPointsService.getAllIcPointsBasicInfo());
        result.put("icPoints", icPointData);

        return success(result);
    }

    /**
     * 测试 GIS 地图数据
     */
    @GetMapping("/gis/map")
    public AjaxResult getGisMapData() {
        Map<String, Object> gisData = new HashMap<>();

        // 获取所有站点用于地图展示
        gisData.put("stops", dimStopsService.getAllStopsForMap());

        return success(gisData);
    }

    /**
     * 测试换乘率分析
     */
    @GetMapping("/transfer/analysis")
    public AjaxResult getTransferAnalysis(@RequestParam(defaultValue = "10") Integer limit) {
        Map<String, Object> analysis = new HashMap<>();

        // 获取换乘率 TOP10 站点
        analysis.put("topTransferStations", factCardTransactionsService.getTopTransferStations(limit));

        return success(analysis);
    }

    /**
     * 测试线路详细数据
     */
    @GetMapping("/line/detail")
    public AjaxResult getLineDetail(@RequestParam String lineName) {
        Map<String, Object> detail = new HashMap<>();

        // 获取线路基本信息
        detail.put("basicInfo", dimLinesService.getLineDetail(lineName));

        // 获取该线路的客流统计
        detail.put("flowStats", factStationFlowService.getFlowByLine(null, null).stream()
                .filter(m -> lineName.equals(String.valueOf(m.get("lineName"))))
                .findFirst()
                .orElse(null));

        return success(detail);
    }
}
