package com.ruoyi.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.MallMember;
import com.ruoyi.system.domain.shop.MallOrder;
import com.ruoyi.system.domain.shop.MallOrderCloseResult;
import com.ruoyi.system.domain.shop.MallOrderCreateRequest;
import com.ruoyi.system.mapper.shop.MallOrderMapper;
import com.ruoyi.system.service.IMallOrderService;
import com.ruoyi.system.service.ISysConfigService;

@ExtendWith(MockitoExtension.class)
class MallOrderServiceImplTest
{
    @Mock private MallOrderMapper orderMapper;
    @Mock private ISysConfigService configService;
    @Mock private TransactionTemplate transactionTemplate;
    @InjectMocks private MallOrderServiceImpl service;

    @Test
    void orderCommandsRequireExplicitOperatorTypeAndId()
    {
        assertDoesNotThrow(() -> IMallOrderService.class.getMethod("paySuccess", Long.class, String.class, String.class, Long.class, String.class, String.class));
        assertDoesNotThrow(() -> IMallOrderService.class.getMethod("cancelOrder", Long.class, String.class, String.class, String.class));
        assertDoesNotThrow(() -> IMallOrderService.class.getMethod("shipOrder", Long.class, String.class, String.class, String.class, String.class));
        assertDoesNotThrow(() -> IMallOrderService.class.getMethod("completeOrder", Long.class, String.class, String.class));
    }

    @Test
    void expiredOrderBatchOwnsNoOuterTransactionAndUsesTransactionTemplate() throws Exception
    {
        assertFalse(MallOrderServiceImpl.class.getDeclaredMethod("closeExpiredOrders").isAnnotationPresent(Transactional.class));
        assertDoesNotThrow(() -> {
            java.lang.reflect.Field field = MallOrderServiceImpl.class.getDeclaredField("transactionTemplate");
            if (!TransactionTemplate.class.equals(field.getType())) throw new AssertionError("transactionTemplate type mismatch");
        });
    }

    @Test
    void expiredOrderBatchReportsScannedSucceededAndFailedCounts() throws Exception
    {
        MallOrder first = new MallOrder();
        first.setOrderId(1L);
        MallOrder second = new MallOrder();
        second.setOrderId(2L);
        when(orderMapper.selectExpiredPendingOrders()).thenReturn(Arrays.asList(first, second));
        when(transactionTemplate.execute(any())).thenReturn(null).thenThrow(new ServiceException("库存释放失败"));

        MallOrderCloseResult result = service.closeExpiredOrders();

        assertEquals(2, result.getScannedCount());
        assertEquals(1, result.getSuccessCount());
        assertEquals(1, result.getFailureCount());
    }

    @Test
    void mapperExposesActivePendingOrderAndQuantityQueries()
    {
        assertDoesNotThrow(() -> MallOrderMapper.class.getMethod("countActivePendingOrders", Long.class));
        assertDoesNotThrow(() -> MallOrderMapper.class.getMethod("sumActivePendingItemQuantity", Long.class));
    }

    @Test
    void rejectsMoreThanFiftyRequestLinesBeforeReadingMember()
    {
        List<MallOrderCreateRequest.Item> items = new ArrayList<MallOrderCreateRequest.Item>();
        for (long skuId = 1; skuId <= 51; skuId++) items.add(item(skuId, 1));

        assertThrows(ServiceException.class, () -> service.createOrder(request(items)));

        verify(orderMapper, never()).selectActiveMember(anyLong());
    }

    @Test
    void rejectsMergedOrderQuantityOverOneHundredBeforeReadingMember()
    {
        MallOrderCreateRequest request = request(Arrays.asList(item(1L, 60), item(1L, 41)));

        assertThrows(ServiceException.class, () -> service.createOrder(request));

        verify(orderMapper, never()).selectActiveMember(anyLong());
    }

    @Test
    void rejectsMemberAtPendingOrderLimitBeforeReadingAddress()
    {
        activeMember();
        when(orderMapper.countActivePendingOrders(7L)).thenReturn(5);

        assertThrows(ServiceException.class, () -> service.createOrder(request(Arrays.asList(item(1L, 1)))));

        verify(orderMapper, never()).selectMemberAddress(7L, 8L);
    }

    @Test
    void rejectsMemberAtLockedQuantityLimitBeforeReadingAddress()
    {
        activeMember();
        when(orderMapper.sumActivePendingItemQuantity(7L)).thenReturn(100L);

        assertThrows(ServiceException.class, () -> service.createOrder(request(Arrays.asList(item(1L, 1)))));

        verify(orderMapper, never()).selectMemberAddress(7L, 8L);
    }

    @Test
    void locksMemberBeforeReturningIdempotentOrder()
    {
        activeMember();
        MallOrder existing = new MallOrder();
        existing.setOrderId(9L);
        when(orderMapper.selectOrderByMemberRequest(7L, "request-1")).thenReturn(existing);
        when(orderMapper.selectOrderById(9L)).thenReturn(existing);

        service.createOrder(request(Arrays.asList(item(1L, 1))));

        InOrder sequence = org.mockito.Mockito.inOrder(orderMapper);
        sequence.verify(orderMapper).selectActiveMember(7L);
        sequence.verify(orderMapper).selectOrderByMemberRequest(7L, "request-1");
        verify(orderMapper, never()).countActivePendingOrders(7L);
    }

    private void activeMember()
    {
        MallMember member = new MallMember();
        member.setMemberId(7L);
        when(orderMapper.selectActiveMember(7L)).thenReturn(member);
    }

    private MallOrderCreateRequest request(List<MallOrderCreateRequest.Item> items)
    {
        MallOrderCreateRequest request = new MallOrderCreateRequest();
        request.setMemberId(7L);
        request.setClientRequestNo("request-1");
        request.setAddressId(8L);
        request.setItems(items);
        return request;
    }

    private MallOrderCreateRequest.Item item(Long skuId, Integer quantity)
    {
        MallOrderCreateRequest.Item item = new MallOrderCreateRequest.Item();
        item.setSkuId(skuId);
        item.setQuantity(quantity);
        return item;
    }
}
