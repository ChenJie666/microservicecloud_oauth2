package com.hxr.user;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hxr.user.dao")
public class UserCenter4001_App {
    public static void main(String[] args) {
        SpringApplication.run(UserCenter4001_App.class,args);
    }
}
