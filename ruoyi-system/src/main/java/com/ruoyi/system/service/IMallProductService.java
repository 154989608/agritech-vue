package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.shop.MallProduct;

public interface IMallProductService
{
    List<MallProduct> selectProductList(MallProduct product);
    MallProduct selectProductById(Long productId);
    int insertProduct(MallProduct product);
    int updateProduct(MallProduct product);
    int deleteProductById(Long productId);
    int updateProductStatus(Long productId, String status, String updateBy);
}
