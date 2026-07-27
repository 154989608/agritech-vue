package com.ruoyi.system.domain.shop;

import java.util.ArrayList;
import java.util.List;

/** 商城运营首页的只读聚合数据。 */
public class ShopDashboard
{
    private Long todayPaidOrderCount = 0L;
    private Long todayPaidAmount = 0L;
    private Long pendingShipmentCount = 0L;
    private Long todayNewMemberCount = 0L;
    private Long pendingPaymentCount = 0L;
    private Long lowStockSkuCount = 0L;
    private List<Trend> salesTrend = new ArrayList<>();
    private List<HotSku> hotSkus = new ArrayList<>();

    public Long getTodayPaidOrderCount() { return todayPaidOrderCount; }
    public void setTodayPaidOrderCount(Long todayPaidOrderCount) { this.todayPaidOrderCount = todayPaidOrderCount; }
    public Long getTodayPaidAmount() { return todayPaidAmount; }
    public void setTodayPaidAmount(Long todayPaidAmount) { this.todayPaidAmount = todayPaidAmount; }
    public Long getPendingShipmentCount() { return pendingShipmentCount; }
    public void setPendingShipmentCount(Long pendingShipmentCount) { this.pendingShipmentCount = pendingShipmentCount; }
    public Long getTodayNewMemberCount() { return todayNewMemberCount; }
    public void setTodayNewMemberCount(Long todayNewMemberCount) { this.todayNewMemberCount = todayNewMemberCount; }
    public Long getPendingPaymentCount() { return pendingPaymentCount; }
    public void setPendingPaymentCount(Long pendingPaymentCount) { this.pendingPaymentCount = pendingPaymentCount; }
    public Long getLowStockSkuCount() { return lowStockSkuCount; }
    public void setLowStockSkuCount(Long lowStockSkuCount) { this.lowStockSkuCount = lowStockSkuCount; }
    public List<Trend> getSalesTrend() { return salesTrend; }
    public void setSalesTrend(List<Trend> salesTrend) { this.salesTrend = salesTrend; }
    public List<HotSku> getHotSkus() { return hotSkus; }
    public void setHotSkus(List<HotSku> hotSkus) { this.hotSkus = hotSkus; }

    public static class Trend
    {
        private String day;
        private Long paidAmount;
        private Long paidOrderCount;
        public String getDay() { return day; }
        public void setDay(String day) { this.day = day; }
        public Long getPaidAmount() { return paidAmount; }
        public void setPaidAmount(Long paidAmount) { this.paidAmount = paidAmount; }
        public Long getPaidOrderCount() { return paidOrderCount; }
        public void setPaidOrderCount(Long paidOrderCount) { this.paidOrderCount = paidOrderCount; }
    }

    public static class HotSku
    {
        private Long skuId;
        private String productName;
        private String skuName;
        private Long salesQuantity;
        private Long paidAmount;
        public Long getSkuId() { return skuId; }
        public void setSkuId(Long skuId) { this.skuId = skuId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public String getSkuName() { return skuName; }
        public void setSkuName(String skuName) { this.skuName = skuName; }
        public Long getSalesQuantity() { return salesQuantity; }
        public void setSalesQuantity(Long salesQuantity) { this.salesQuantity = salesQuantity; }
        public Long getPaidAmount() { return paidAmount; }
        public void setPaidAmount(Long paidAmount) { this.paidAmount = paidAmount; }
    }
}
