package com.hxr.user.config;

import com.hxr.springcloud.entities.user.constants.UserCenterMQ;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    //TODO 将RabbitMQ的交换机对象交给容器管理
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(UserCenterMQ.MQ_EXCHANGE_USER);
    }

}
