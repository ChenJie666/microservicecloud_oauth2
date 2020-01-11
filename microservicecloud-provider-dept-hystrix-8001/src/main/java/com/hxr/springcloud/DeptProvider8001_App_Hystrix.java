package com.hxr.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //TODO 启动服务后注册到Eureka中
@EnableDiscoveryClient  //TODO 服务发现
@EnableCircuitBreaker   //TODO 对hystrix熔断机制的支持
public class DeptProvider8001_App_Hystrix {

    public static void main(String[] args){
        SpringApplication.run(DeptProvider8001_App_Hystrix.class, args);
    }
}
