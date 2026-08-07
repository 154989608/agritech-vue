package com.ruoyi.framework.web.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.util.ReflectionTestUtils;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.shop.app.AppMemberSession;

@ExtendWith(MockitoExtension.class)
class AppMemberAuthServiceTest
{
    @Mock private RedisCache redisCache;
    @Mock private RedisTemplate<String, Object> redisTemplate;
    @Mock private SetOperations<String, Object> setOperations;
    @InjectMocks private AppMemberAuthService service;

    @BeforeEach
    void setUp()
    {
        redisCache.redisTemplate = redisTemplate;
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
    }

    @Test
    void logoutRemovesTokenFromMemberReverseIndex()
    {
        AppMemberSession session = new AppMemberSession();
        session.setMemberId(7L);
        when(redisCache.getCacheObject("app:member:token:token-a")).thenReturn(session);

        service.logout("token-a");

        verify(redisCache).deleteObject("app:member:token:token-a");
        verify(setOperations).remove("app:member:tokens:7", "token-a");
    }

    @Test
    void storedSessionIsAddedToMemberReverseIndex() throws Exception
    {
        AppMemberSession session = new AppMemberSession();
        session.setMemberId(7L);
        ReflectionTestUtils.setField(service, "sessionMinutes", 60);
        assertDoesNotThrow(() -> {
            Method method = AppMemberAuthService.class.getDeclaredMethod("storeSession", String.class, AppMemberSession.class);
            method.setAccessible(true);
            method.invoke(service, "token-a", session);
        });

        verify(redisCache).setCacheObject("app:member:token:token-a", session, 60, TimeUnit.MINUTES);
        verify(setOperations).add("app:member:tokens:7", "token-a");
        verify(redisCache).expire("app:member:tokens:7", 60, TimeUnit.MINUTES);
    }
}
