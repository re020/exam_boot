package com.exam.core.config;

import com.exam.core.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lth
 * @version 1.0.0
 * @date
 */
@Configuration
public class RabbitMqConfig {

    /**
     *功能描述 自定义序列化转换器,默认的是jdk的,这里把它转换为json的
     * @author lth
     * @param
     * @return org.springframework.amqp.support.converter.MessageConverter
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 队列
     */
    @Bean
    public Queue createQueue(){
        return new Queue(MqConstant.SUBMIT_EXAM_QUEUE,true);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(MqConstant.SUBMIT_EXAM_QUEUE);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(createQueue()).to(exchange()).withQueueName();
    }
}
