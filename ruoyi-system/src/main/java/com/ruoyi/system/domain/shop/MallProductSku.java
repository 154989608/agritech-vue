package com.ruoyi.system.domain.shop;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MallProductSku
{
    private Long skuId;
    private Long productId;
    @NotBlank(message = "SKU编码不能为空") private String skuCode;
    @NotBlank(message = "SKU名称不能为空") private String skuName;
    private String specValuesJson;
    private String imageUrl;
    @NotNull(message = "销售价不能为空") private Long salePrice;
    private Long marketPrice;
    private Long availableStock;
    private Long lockedStock;
    private Long warningStock;
    private String status;
    private Integer version;
    public Long getSkuId() { return skuId; } public void setSkuId(Long skuId) { this.skuId = skuId; }
    public Long getProductId() { return productId; } public void setProductId(Long productId) { this.productId = productId; }
    public String getSkuCode() { return skuCode; } public void setSkuCode(String skuCode) { this.skuCode = skuCode; }
    public String getSkuName() { return skuName; } public void setSkuName(String skuName) { this.skuName = skuName; }
    public String getSpecValuesJson() { return specValuesJson; } public void setSpecValuesJson(String specValuesJson) { this.specValuesJson = specValuesJson; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Long getSalePrice() { return salePrice; } public void setSalePrice(Long salePrice) { this.salePrice = salePrice; }
    public Long getMarketPrice() { return marketPrice; } public void setMarketPrice(Long marketPrice) { this.marketPrice = marketPrice; }
    public Long getAvailableStock() { return availableStock; } public void setAvailableStock(Long availableStock) { this.availableStock = availableStock; }
    public Long getLockedStock() { return lockedStock; } public void setLockedStock(Long lockedStock) { this.lockedStock = lockedStock; }
    public Long getWarningStock() { return warningStock; } public void setWarningStock(Long warningStock) { this.warningStock = warningStock; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
    public Integer getVersion() { return version; } public void setVersion(Integer version) { this.version = version; }
}
