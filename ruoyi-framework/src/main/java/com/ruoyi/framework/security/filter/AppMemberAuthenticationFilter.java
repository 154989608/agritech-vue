package com.ruoyi.framework.security.filter;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.AppMemberAuthService;
import com.ruoyi.system.domain.shop.app.AppMemberSession;

@Component public class AppMemberAuthenticationFilter extends OncePerRequestFilter {
    public static final String MEMBER_TOKEN_KEY=AppMemberAuthService.MEMBER_TOKEN_KEY;
    @Autowired private AppMemberAuthService authService;
    @Override protected boolean shouldNotFilter(HttpServletRequest request){return !request.getServletPath().startsWith("/app/shop");}
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws ServletException,IOException{String header=request.getHeader("Authorization");String token=header!=null&&header.startsWith("Bearer ")?header.substring(7):null;AppMemberSession session=authService.getSession(token);if(session!=null&&SecurityContextHolder.getContext().getAuthentication()==null)SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(session,null,Collections.emptyList()));chain.doFilter(request,response);}
}
