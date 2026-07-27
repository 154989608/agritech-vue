package com.ruoyi.web.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IShopDashboardService;

@RestController
@RequestMapping("/shop/dashboard")
public class ShopDashboardController
{
    @Autowired
    private IShopDashboardService dashboardService;

    @PreAuthorize("@ss.hasPermi('shop:dashboard:query')")
    @GetMapping
    public AjaxResult getDashboard()
    {
        return AjaxResult.success(dashboardService.getDashboard());
    }
}
