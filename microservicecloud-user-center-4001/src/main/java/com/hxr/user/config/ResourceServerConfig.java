package com.hxr.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableResourceServer //TODO
@EnableWebSecurity //TODO
@EnableGlobalMethodSecurity(prePostEnabled = true)  //TODO 启用全局认证机制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //TODO 允许跨站点请求伪造
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED); //TODO
                    }
                })
                .and().authorizeRequests()
                .antMatchers("/users-anon/**","/wechat/**").permitAll() //TODO 开放不需要进行验证的端口
                .anyRequest().authenticated().and().httpBasic();    //TODO 所有的请求都需要进行验证，验证方式为httpBasic
    }
}
