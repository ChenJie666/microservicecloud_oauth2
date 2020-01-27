package com.hxr.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfigure {

    /**
     * 如何做到swagger只在测试环境中使用，在发布环境中禁用？？？
     * 1.首先需要获取所使用的application.yml的使用环境
     * 2.根据获取的环境获取flag的值
     * 3.设置 enbale(flag) ，符合条件则开启swagger
     *
     * @return
     */

    @Bean
    public Docket docket(Environment environment){

        Profiles profiles = Profiles.of("test", "dev"); //TODO 根据字符串获取Profiles对象
        boolean flag = environment.acceptsProfiles(profiles);   //TODO 从环境变量中判断当前使用的配置文件是否是"test"或"dev",若是则返回true，否则返回false

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) //TODO 设置swagger的描述界面的信息
                .enable(flag)   //TODO 是否启用swagger，默认为true；如果为false，则网页不能访问swagger
                .select()   //TODO
                .apis(RequestHandlerSelectors.basePackage("com.hxr.springcloud.controller"))    //TODO  .basePackage指定扫描的包   .any表示扫描全部   .none表示不扫描
//                .apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))   //TODO  .withClassAnnotation扫描类上的注解，参数是注解的反射对象   .withMethodAnnotation扫描方法上的注解，参数是注解的反射对象
                .paths(PathSelectors.any()) //TODO  .ant指定扫描的路径   .any表示扫描路径   .none表示不扫描
                .build();
    }

    private ApiInfo apiInfo(){  //TODO swagger的描述界面的信息
        return new ApiInfo(
                "CJ的API文档",
                "microservicecloud-provider-dept-8001",
                "v0.1",
                "https://github.com/ChenJie666/microservicecloud_oauth2",
                new Contact("陈捷","www.xx.com","792965772@qq.com"),  //TODO 作者信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>()
        );
    }

}
