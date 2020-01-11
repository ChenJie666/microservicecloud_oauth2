package com.hxr.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //TODO 启动服务后注册到Eureka中
@EnableDiscoveryClient  //TODO 服务发现
@EnableCircuitBreaker
public class DeptProvider8002_App {

    public static void main(String[] args){
        SpringApplication.run(DeptProvider8002_App.class, args);
    }
}
