package com.ruoyi.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.app.AppCartPreviewRequest;
import com.ruoyi.system.domain.shop.app.AppHomeDto;
import com.ruoyi.system.domain.shop.app.AppProductDto;
import com.ruoyi.system.mapper.shop.AppShopMapper;

@ExtendWith(MockitoExtension.class)
class AppShopServiceImplTest
{
    @Mock private AppShopMapper appShopMapper;
    @InjectMocks private AppShopServiceImpl service;

    @Test
    void mapperExposesDedicatedLatestAndHotProductQueries()
    {
        assertDoesNotThrow(() -> AppShopMapper.class.getMethod("selectLatestProducts"));
        assertDoesNotThrow(() -> AppShopMapper.class.getMethod("selectHotProducts"));
    }

    @Test
    void homeUsesDedicatedLimitedProductQueries()
    {
        List<AppProductDto> latest = Arrays.asList(new AppProductDto());
        List<AppProductDto> hot = Arrays.asList(new AppProductDto());
        when(appShopMapper.selectActiveBanners()).thenReturn(Collections.emptyList());
        when(appShopMapper.selectEnabledCategories()).thenReturn(Collections.emptyList());
        when(appShopMapper.selectLatestProducts()).thenReturn(latest);
        when(appShopMapper.selectHotProducts()).thenReturn(hot);

        AppHomeDto home = service.getHome();

        assertSame(latest, home.getLatestProducts());
        assertSame(hot, home.getHotProducts());
        verify(appShopMapper, never()).selectPublishedProducts(null, null);
    }

    @Test
    void rejectsMoreThanFiftyPreviewLinesBeforeQueryingProducts()
    {
        List<AppCartPreviewRequest.Item> items = new ArrayList<AppCartPreviewRequest.Item>();
        for (long skuId = 1; skuId <= 51; skuId++) items.add(item(skuId, 1));

        assertThrows(ServiceException.class, () -> service.previewCart(request(items)));

        verifyNoInteractions(appShopMapper);
    }

    @Test
    void rejectsMergedSkuQuantityOverNinetyNineBeforeQueryingProducts()
    {
        AppCartPreviewRequest request = request(Arrays.asList(item(1L, 50), item(1L, 50)));

        assertThrows(ServiceException.class, () -> service.previewCart(request));

        verifyNoInteractions(appShopMapper);
    }

    private AppCartPreviewRequest request(List<AppCartPreviewRequest.Item> items)
    {
        AppCartPreviewRequest request = new AppCartPreviewRequest();
        request.setItems(items);
        return request;
    }

    private AppCartPreviewRequest.Item item(Long skuId, Integer quantity)
    {
        AppCartPreviewRequest.Item item = new AppCartPreviewRequest.Item();
        item.setSkuId(skuId);
        item.setQuantity(quantity);
        return item;
    }
}
