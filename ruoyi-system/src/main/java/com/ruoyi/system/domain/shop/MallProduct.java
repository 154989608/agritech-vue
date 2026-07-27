package com.ruoyi.system.domain.shop;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.ruoyi.common.core.domain.BaseEntity;

public class MallProduct extends BaseEntity
{
    private Long productId;
    @NotNull(message = "商品分类不能为空") private Long categoryId;
    @NotBlank(message = "商品名称不能为空") private String productName;
    private String subtitle;
    private String mainImage;
    private String imagesJson;
    private String detailHtml;
    private String productParamsJson;
    private String specSchemaJson;
    private String status;
    private Integer version;
    private List<MallProductSku> skus;
    public Long getProductId() { return productId; } public void setProductId(Long productId) { this.productId = productId; }
    public Long getCategoryId() { return categoryId; } public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getProductName() { return productName; } public void setProductName(String productName) { this.productName = productName; }
    public String getSubtitle() { return subtitle; } public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public String getMainImage() { return mainImage; } public void setMainImage(String mainImage) { this.mainImage = mainImage; }
    public String getImagesJson() { return imagesJson; } public void setImagesJson(String imagesJson) { this.imagesJson = imagesJson; }
    public String getDetailHtml() { return detailHtml; } public void setDetailHtml(String detailHtml) { this.detailHtml = detailHtml; }
    public String getProductParamsJson() { return productParamsJson; } public void setProductParamsJson(String productParamsJson) { this.productParamsJson = productParamsJson; }
    public String getSpecSchemaJson() { return specSchemaJson; } public void setSpecSchemaJson(String specSchemaJson) { this.specSchemaJson = specSchemaJson; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
    public Integer getVersion() { return version; } public void setVersion(Integer version) { this.version = version; }
    public List<MallProductSku> getSkus() { return skus; } public void setSkus(List<MallProductSku> skus) { this.skus = skus; }
}
