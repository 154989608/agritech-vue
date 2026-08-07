package com.ruoyi.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.shop.MallMember;
import com.ruoyi.system.mapper.shop.MallMemberMapper;

@ExtendWith(MockitoExtension.class)
class MallMemberServiceImplTest
{
    @Mock private MallMemberMapper memberMapper;
    @Mock private RedisCache redisCache;
    @InjectMocks private MallMemberServiceImpl service;

    @Test
    void disablingMemberRevokesEveryIssuedToken()
    {
        when(memberMapper.updateMemberStatus(org.mockito.ArgumentMatchers.any(MallMember.class))).thenReturn(1);
        when(redisCache.<String>getCacheSet("app:member:tokens:7")).thenReturn(new HashSet<String>(Arrays.asList("token-a", "token-b")));

        assertEquals(1, service.updateStatus(7L, "1"));

        verify(redisCache).deleteObject("app:member:token:token-a");
        verify(redisCache).deleteObject("app:member:token:token-b");
        verify(redisCache).deleteObject("app:member:tokens:7");
    }
}
