package com.hxr.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {   //TODO 词类等同于applicationContext.xml

    //TODO 配置了RestTemplate类的自动注入（@Autowired）
    @Bean
    @LoadBalanced   //TODO Ribbon负载均衡只需要加这个标签
    public RestTemplate getRestTemplate(){  //TODO 提供了多种便捷访问Http服务的方法，是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具类
        return new RestTemplate();
    }

    @Bean
    public IRule myRule(){
//        return new RoundRobinRule();  //TODO 默认的轮询算法
//        return new RandomRule();    //TODO 用随机算法替换轮训算法
        return new RetryRule(); //TODO retry算法，如果有个服务宕机了，会尝试几次后自动跳过
    }

}
