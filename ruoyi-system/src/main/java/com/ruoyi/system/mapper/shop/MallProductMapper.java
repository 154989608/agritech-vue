package com.ruoyi.system.mapper.shop;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.shop.MallProduct;
import com.ruoyi.system.domain.shop.MallProductSku;

public interface MallProductMapper
{
    List<MallProduct> selectProductList(MallProduct product);
    MallProduct selectProductById(Long productId);
    List<MallProductSku> selectSkusByProductId(Long productId);
    int insertProduct(MallProduct product);
    int updateProduct(MallProduct product);
    int deleteProductById(Long productId);
    int insertSku(MallProductSku sku);
    int updateSku(MallProductSku sku);
    int softDeleteSku(@Param("productId") Long productId, @Param("skuId") Long skuId);
    int countPublishableSkus(Long productId);
    int updateProductStatus(MallProduct product);
    int countProductsByCategoryId(Long categoryId);
}
