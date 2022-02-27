package com.exam.core.config;

import com.exam.core.constant.CoreConstant;
import com.exam.core.interceptor.CorsInterceptor;
import com.exam.core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置拦截器
 *
 * @author yds
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**", "/file/**");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**", "/file/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + CoreConstant.IMG_URL, "file:" + CoreConstant.PAPER_URL);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}