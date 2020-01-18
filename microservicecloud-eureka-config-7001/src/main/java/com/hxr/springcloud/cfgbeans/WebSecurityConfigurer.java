package com.hxr.springcloud.cfgbeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 访问配置中心需要输入账号密码，账号密码在配置文件中设置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  //TODO csrf是跨站请求伪造,关闭csrf保护
        http.authorizeRequests()
                .antMatchers("/eureka").hasRole("eureka_client")
                .antMatchers("/").hasRole("view")
//                .antMatchers().access("hasRole('view') and hasRole('visit')") //TODO 拥有所有的角色才可访问
//                .antMatchers().hasAnyRole("view","visit")   //TODO 拥有其中一个角色即可访问
                .anyRequest().authenticated()   //TODO 所有的请求都需要进行验证（如果没有写，则只有列出的请求路径需要验证）
                .and()  //TODO 返回一个HttpSecurity对象
                .formLogin()   //TODO formLogin(列表形式)和httpBasic(弹窗形式)是两种登陆形式
//                .loginPage("/loginmy").permitAll()   //TODO 重定向到登录页面，并开放访问该页面的权限(否则重定向到默认页面)
                .and()
                .httpBasic()   //TODO httpBasic用于Web Service API登录
                ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(passwordEncoder().encode("abc123"))
                .roles("eureka_client")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("view");
    }

}
