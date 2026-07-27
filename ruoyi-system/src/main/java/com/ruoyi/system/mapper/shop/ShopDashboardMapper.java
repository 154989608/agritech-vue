package com.ruoyi.system.mapper.shop;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.shop.ShopDashboard;

public interface ShopDashboardMapper
{
    ShopDashboard selectSummary(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

    List<ShopDashboard.Trend> selectSalesTrend(@Param("start") Date start, @Param("end") Date end);

    List<ShopDashboard.HotSku> selectHotSkus(@Param("start") Date start, @Param("end") Date end);
}
