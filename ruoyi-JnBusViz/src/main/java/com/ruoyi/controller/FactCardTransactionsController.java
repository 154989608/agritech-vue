package com.ruoyi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.FactCardTransactions;
import com.ruoyi.service.FactCardTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 公交 IC 卡交易 Controller
 * 
 * @author 若依
 */
@RestController
@RequestMapping("/bus/card/transactions")
public class FactCardTransactionsController extends BaseController {
    
    @Autowired
    private FactCardTransactionsService factCardTransactionsService;
    
    /**
     * 查询 IC 卡交易列表
     */
    @GetMapping("/list")
    public AjaxResult list(FactCardTransactions factCardTransactions) {
        List<FactCardTransactions> list = factCardTransactionsService.selectFactCardTransactionsList(factCardTransactions);
        return success(list);
    }
    
    /**
     * 按卡种统计客流量
     */
    @GetMapping("/byCardType")
    public AjaxResult getFlowByCardType(@RequestParam(required = false) Integer year,
                                        @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> result = factCardTransactionsService.getFlowByCardType(year, month);
        return success(result);
    }
    
    /**
     * 获取换乘率 TOP10 站点
     */
    @GetMapping("/transfer/top10")
    public AjaxResult getTopTransferStations(@RequestParam(defaultValue = "10") Integer limit) {
        List<Map<String, Object>> result = factCardTransactionsService.getTopTransferStations(limit);
        return success(result);
    }
    
    /**
     * 按站点统计卡种分布
     */
    @GetMapping("/station/cardTypeDist")
    public AjaxResult getCardTypeDistByStation(@RequestParam String stopName,
                                               @RequestParam(required = false) Integer year,
                                               @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> result = factCardTransactionsService.getCardTypeDistByStation(stopName, year, month);
        return success(result);
    }
    
    /**
     * 获取线路卡种客流对比
     */
    @GetMapping("/line/cardTypeFlow")
    public AjaxResult getCardTypeFlowByLine(@RequestParam String lineName,
                                            @RequestParam(required = false) Integer year,
                                            @RequestParam(required = false) Integer month) {
        List<Map<String, Object>> result = factCardTransactionsService.getCardTypeFlowByLine(lineName, year, month);
        return success(result);
    }
    
    /**
     * 获取日均交易量趋势
     */
    @GetMapping("/trend/daily")
    public AjaxResult getDailyTransactionTrend(@RequestParam(defaultValue = "30") Integer days) {
        List<Map<String, Object>> result = factCardTransactionsService.getDailyTransactionTrend(days);
        return success(result);
    }
}
