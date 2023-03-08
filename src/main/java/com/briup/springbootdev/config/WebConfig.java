package com.briup.springbootdev.config;

import com.briup.springbootdev.web.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web层配置类； 提供用户自定义组件对象 解决XXX具体问题
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {
        System.out.println("创建一个配置类对象");
    }

    @Autowired //注入我们通过spring创建的bean对象
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/auth/**");
    }
}
