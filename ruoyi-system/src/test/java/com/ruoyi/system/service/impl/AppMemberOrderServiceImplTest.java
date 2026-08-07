package com.ruoyi.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderItem;
import com.ruoyi.system.mapper.shop.MallOrderMapper;

@ExtendWith(MockitoExtension.class)
class AppMemberOrderServiceImplTest
{
    @Mock private MallOrderMapper orderMapper;
    @InjectMocks private AppMemberOrderServiceImpl service;

    @Test
    void mapperExposesBatchOrderItemQuery()
    {
        assertDoesNotThrow(() -> MallOrderMapper.class.getMethod("selectOrderItemsByOrderIds", List.class));
    }

    @Test
    void listLoadsAllPageItemsWithOneBatchQuery()
    {
        MallOrder first = order(1L);
        MallOrder second = order(2L);
        MallOrderItem firstItem = item(1L, 11L);
        MallOrderItem secondItem = item(2L, 22L);
        when(orderMapper.selectOrderList(any(MallOrder.class))).thenReturn(Arrays.asList(first, second));
        when(orderMapper.selectOrderItemsByOrderIds(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(secondItem, firstItem));

        service.list(7L, null);

        assertEquals(11L, first.getItems().get(0).getSkuId());
        assertEquals(22L, second.getItems().get(0).getSkuId());
        verify(orderMapper).selectOrderItemsByOrderIds(Arrays.asList(1L, 2L));
        verify(orderMapper, never()).selectOrderItems(anyLong());
    }

    private MallOrder order(Long orderId)
    {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        return order;
    }

    private MallOrderItem item(Long orderId, Long skuId)
    {
        MallOrderItem item = new MallOrderItem();
        item.setOrderId(orderId);
        item.setSkuId(skuId);
        return item;
    }
}
