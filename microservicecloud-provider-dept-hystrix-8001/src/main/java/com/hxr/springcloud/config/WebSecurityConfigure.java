package com.hxr.springcloud.config;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.WebSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/swagger-ui.html"
                ,"/v2/api-docs" //TODO swagger api json
//                ,"/swagger-resources" //TODO 用来获取aip-docs的URI
                ,"/swagger-resources/**"
                ,"/configuration/ui"
//                ,"/configuration/security"
//                ,"/webjars/**"
                ,"/dept/list"
        );
    }
}