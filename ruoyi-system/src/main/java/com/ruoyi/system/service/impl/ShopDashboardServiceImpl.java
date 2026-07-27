package com.ruoyi.system.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.shop.ShopDashboard;
import com.ruoyi.system.mapper.shop.ShopDashboardMapper;
import com.ruoyi.system.service.IShopDashboardService;

@Service
public class ShopDashboardServiceImpl implements IShopDashboardService
{
    private static final ZoneId SHOP_ZONE = ZoneId.of("Asia/Shanghai");

    @Autowired
    private ShopDashboardMapper dashboardMapper;

    @Override
    public ShopDashboard getDashboard()
    {
        LocalDate today = LocalDate.now(SHOP_ZONE);
        Date dayStart = toDate(today);
        Date dayEnd = toDate(today.plusDays(1));
        ShopDashboard dashboard = dashboardMapper.selectSummary(dayStart, dayEnd);
        if (dashboard == null)
        {
            dashboard = new ShopDashboard();
        }
        dashboard.setSalesTrend(dashboardMapper.selectSalesTrend(toDate(today.minusDays(6)), dayEnd));
        dashboard.setHotSkus(dashboardMapper.selectHotSkus(toDate(today.minusDays(29)), dayEnd));
        return dashboard;
    }

    private Date toDate(LocalDate date)
    {
        Instant instant = date.atStartOfDay(SHOP_ZONE).toInstant();
        return Date.from(instant);
    }
}
