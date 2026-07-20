package com.ruoyi.service.impl;

import com.ruoyi.domain.FactCardTransactions;
import com.ruoyi.mapper.FactCardTransactionsMapper;
import com.ruoyi.service.FactCardTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 公交 IC 卡交易 Service 实现类
 * 
 * @author 若依
 */
@Service
public class FactCardTransactionsServiceImpl implements FactCardTransactionsService {
    
    @Autowired
    private FactCardTransactionsMapper factCardTransactionsMapper;
    
    @Override
    public List<FactCardTransactions> selectFactCardTransactionsList(FactCardTransactions factCardTransactions) {
        return factCardTransactionsMapper.selectFactCardTransactionsList(factCardTransactions);
    }
    
    @Override
    public List<Map<String, Object>> getFlowByCardType(Integer year, Integer month) {
        return factCardTransactionsMapper.getFlowByCardType(year, month);
    }
    
    @Override
    public List<Map<String, Object>> getTopTransferStations(Integer limit) {
        return factCardTransactionsMapper.getTopTransferStations(limit);
    }
    
    @Override
    public List<Map<String, Object>> getCardTypeDistByStation(String stopName, Integer year, Integer month) {
        return factCardTransactionsMapper.getCardTypeDistByStation(stopName, year, month);
    }
    
    @Override
    public List<Map<String, Object>> getCardTypeFlowByLine(String lineName, Integer year, Integer month) {
        return factCardTransactionsMapper.getCardTypeFlowByLine(lineName, year, month);
    }
    
    @Override
    public List<Map<String, Object>> getDailyTransactionTrend(Integer days) {
        return factCardTransactionsMapper.getDailyTransactionTrend(days);
    }
}
