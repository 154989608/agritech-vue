package com.ruoyi.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.MallProduct;
import com.ruoyi.system.domain.shop.MallProductSku;
import com.ruoyi.system.domain.shop.MallCategory;
import com.ruoyi.system.mapper.shop.MallCategoryMapper;
import com.ruoyi.system.mapper.shop.MallProductMapper;

@ExtendWith(MockitoExtension.class)
class MallProductServiceImplTest
{
    @Mock private MallProductMapper productMapper;
    @Mock private MallCategoryMapper categoryMapper;
    @InjectMocks private MallProductServiceImpl service;

    @Test
    void mapperExposesProductScopedSoftDelete()
    {
        assertDoesNotThrow(() -> MallProductMapper.class.getMethod("softDeleteSku", Long.class, Long.class));
    }

    @Test
    void rejectsRemovingSkuWithLockedStockBeforeUpdatingProduct()
    {
        MallProduct existing = existingProduct();
        existing.getSkus().get(1).setLockedStock(1L);
        MallProduct submitted = product(sku(1L, 0L));

        assertThrows(ServiceException.class, () -> service.updateProduct(submitted));
        verify(productMapper, never()).updateProduct(any(MallProduct.class));
    }

    @Test
    void rejectsSkuOwnedByAnotherProductBeforeUpdatingProduct()
    {
        existingProduct();
        MallProduct submitted = product(sku(999L, 0L));

        assertThrows(ServiceException.class, () -> service.updateProduct(submitted));
        verify(productMapper, never()).updateProduct(any(MallProduct.class));
    }

    @Test
    void softDeletesExistingSkuOmittedFromSubmission()
    {
        existingProduct();
        MallCategory category = new MallCategory();
        category.setLevel(2);
        category.setStatus("0");
        when(categoryMapper.selectCategoryById(10L)).thenReturn(category);
        when(productMapper.updateSku(any(MallProductSku.class))).thenReturn(1);
        when(productMapper.softDeleteSku(100L, 2L)).thenReturn(1);

        service.updateProduct(product(sku(1L, 0L)));

        verify(productMapper).softDeleteSku(100L, 2L);
    }

    private MallProduct product(MallProductSku... skus)
    {
        MallProduct product = new MallProduct();
        product.setProductId(100L);
        product.setCategoryId(10L);
        product.setDetailHtml("");
        product.setSkus(skus.length == 0 ? Collections.<MallProductSku>emptyList() : Arrays.asList(skus));
        return product;
    }

    private MallProduct existingProduct()
    {
        MallProduct existing = product(sku(1L, 0L), sku(2L, 0L));
        when(productMapper.selectProductById(100L)).thenReturn(existing);
        when(productMapper.selectSkusByProductId(100L)).thenReturn(existing.getSkus());
        return existing;
    }

    private MallProductSku sku(Long skuId, Long lockedStock)
    {
        MallProductSku sku = new MallProductSku();
        sku.setSkuId(skuId);
        sku.setSkuCode("SKU-" + skuId);
        sku.setSalePrice(100L);
        sku.setLockedStock(lockedStock);
        return sku;
    }
}
