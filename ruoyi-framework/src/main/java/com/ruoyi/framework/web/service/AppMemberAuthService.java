package com.ruoyi.framework.web.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.shop.MallMember;
import com.ruoyi.system.domain.shop.app.AppMemberLoginDto;
import com.ruoyi.system.domain.shop.app.AppMemberSession;
import com.ruoyi.system.mapper.shop.MallMemberMapper;

@Component public class AppMemberAuthService {
    public static final String MEMBER_TOKEN_KEY="app:member:token:";
    @Value("${wechat.mini-program.app-id:}") private String appId;
    @Value("${wechat.mini-program.app-secret:}") private String appSecret;
    @Value("${wechat.mini-program.session-minutes:43200}") private Integer sessionMinutes;
    @Autowired private MallMemberMapper memberMapper; @Autowired private RedisCache redisCache;
    public AppMemberLoginDto login(String code){ if(StringUtils.isEmpty(appId)||StringUtils.isEmpty(appSecret))throw new ServiceException("微信小程序登录未配置"); JSONObject response=code2Session(code); String openId=response.getString("openid"); if(StringUtils.isEmpty(openId))throw new ServiceException("微信登录失败"); MallMember member=memberMapper.selectMemberByAppOpen(appId,openId); if(member==null){member=new MallMember();member.setAppId(appId);member.setOpenId(openId);member.setUnionId(response.getString("unionid"));member.setStatus("0");memberMapper.insertAppMember(member);}else memberMapper.updateMemberLogin(member.getMemberId()); if(!"0".equals(member.getStatus()))throw new ServiceException("会员已禁用"); String token=IdUtils.fastUUID(); AppMemberSession session=new AppMemberSession();session.setMemberId(member.getMemberId());session.setAppId(appId);redisCache.setCacheObject(MEMBER_TOKEN_KEY+token,session,sessionMinutes,TimeUnit.MINUTES); AppMemberLoginDto result=new AppMemberLoginDto();result.setToken(token);result.setMemberId(member.getMemberId());result.setNickname(member.getNickname());return result; }
    public AppMemberSession getSession(String token){return StringUtils.isEmpty(token)?null:(AppMemberSession)redisCache.getCacheObject(MEMBER_TOKEN_KEY+token);}
    public void logout(String token){if(StringUtils.isNotEmpty(token))redisCache.deleteObject(MEMBER_TOKEN_KEY+token);}
    private JSONObject code2Session(String code){HttpURLConnection connection=null;try{String url="https://api.weixin.qq.com/sns/jscode2session?appid="+URLEncoder.encode(appId,"UTF-8")+"&secret="+URLEncoder.encode(appSecret,"UTF-8")+"&js_code="+URLEncoder.encode(code,"UTF-8")+"&grant_type=authorization_code";connection=(HttpURLConnection)new URL(url).openConnection();connection.setConnectTimeout(5000);connection.setReadTimeout(5000);connection.setRequestMethod("GET");int status=connection.getResponseCode();InputStream stream=status>=200&&status<300?connection.getInputStream():connection.getErrorStream();String body=read(stream);JSONObject result=JSON.parseObject(body);if(status<200||status>=300||result.containsKey("errcode"))throw new ServiceException("微信登录失败");return result;}catch(ServiceException e){throw e;}catch(Exception e){throw new ServiceException("微信登录服务暂不可用");}finally{if(connection!=null)connection.disconnect();}}
    private String read(InputStream stream)throws Exception{StringBuilder body=new StringBuilder();try(BufferedReader reader=new BufferedReader(new InputStreamReader(stream,StandardCharsets.UTF_8))){String line;while((line=reader.readLine())!=null)body.append(line);}return body.toString();}
}
