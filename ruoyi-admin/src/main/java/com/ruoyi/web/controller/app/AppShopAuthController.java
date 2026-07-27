package com.ruoyi.web.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.framework.web.service.AppMemberAuthService;
import com.ruoyi.system.domain.shop.app.AppMemberLoginRequest;

@RestController @RequestMapping("/app/shop") public class AppShopAuthController {
    @Autowired private AppMemberAuthService authService;
    @Anonymous @RateLimiter(time=60,count=30,limitType=LimitType.IP) @PostMapping("/auth/login") public AjaxResult login(@Valid @RequestBody AppMemberLoginRequest request){return AjaxResult.success(authService.login(request.getCode()));}
    @PostMapping("/auth/logout") public AjaxResult logout(HttpServletRequest request){String header=request.getHeader("Authorization");authService.logout(header!=null&&header.startsWith("Bearer ")?header.substring(7):null);return AjaxResult.success();}
}
