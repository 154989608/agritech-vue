package com.ruoyi.system.mapper.shop;

import java.util.List;
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
    int countPublishableSkus(Long productId);
    int updateProductStatus(MallProduct product);
    int countProductsByCategoryId(Long categoryId);
}
