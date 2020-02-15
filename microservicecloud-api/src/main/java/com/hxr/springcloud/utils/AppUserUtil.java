package com.hxr.springcloud.utils;

import com.alibaba.fastjson.JSONObject;
import com.hxr.springcloud.entities.user.LoginAppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

/**
 * 从当前上下文中，获取当前用户LoginAppUser对象
 * TODO 原理：？？？？？？？？
 *
 * 经过spring security认证后，security会把一个SecurityContextImpl对象存储到session中。
 * 必须在spring security过滤器执行中执行，否则在过滤链执行完时org.springframework.security.web.context.SecurityContextPersistenceFilter类会调用SecurityContextHolder.clearContext();而把SecurityContextHolder清空，所以会得到null（security的过滤器应该放最前面）
 *
 */
public class AppUserUtil {

    public static LoginAppUser getLoginAppUser(){
        SecurityContext context = SecurityContextHolder.getContext();//TODO 获取上下文环境
        Authentication authentication = context.getAuthentication();//TODO 获取当前登陆的凭证

        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            Authentication userAuthentication = oAuth2Auth.getUserAuthentication();

            if (userAuthentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) userAuthentication;
                Object principal = authenticationToken.getPrincipal();
                if (principal instanceof LoginAppUser) {
                    return (LoginAppUser)principal;
                }

                Map map = (Map) authentication.getDetails();
                map = (Map) map.get("principal");//TODO 返回key="principal"的value的值，并强转为Map

                return JSONObject.parseObject(JSONObject.toJSONString(map),LoginAppUser.class);
            }
        }

        return null;
    }

}
