package com.ruoyi.framework.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.shop.app.AppMemberSession;

public final class AppMemberContext {
    private AppMemberContext() { }
    public static Long getMemberId(){Long memberId=getMemberIdOrNull();if(memberId==null)throw new ServiceException("会员登录已失效");return memberId;}
    public static Long getMemberIdOrNull(){Authentication authentication=SecurityContextHolder.getContext().getAuthentication();return authentication!=null&&authentication.getPrincipal() instanceof AppMemberSession?((AppMemberSession)authentication.getPrincipal()).getMemberId():null;}
}
