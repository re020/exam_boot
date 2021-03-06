package com.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * SpringBoot启动类
 *
 * @author
 */
@MapperScan("com.exam.*.mapper")
@EnableCaching
@EnableScheduling
@EnableRabbit
@EnableTransactionManagement
@CrossOrigin
@ServletComponentScan
@SpringBootApplication
@PropertySource({"classpath:resource.properties"})
public class ExamBootApplication   extends SpringBootServletInitializer{


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ExamBootApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(ExamBootApplication.class, args);
    }

}
