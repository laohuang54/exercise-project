package com.fth.config;

import com.fth.utils.AdminInterceptor;
import com.fth.utils.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 只保留 @Configuration 注解，删除冗余的 @Component
@Configuration
public class MvcConfig implements WebMvcConfigurer { // 实现接口，而非继承类

    @Autowired
    private UserInterceptor userInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 用户拦截器：拦截 /user/** 下的接口，排除登录、注册
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/user/**","/essay/**","/common/**","/shop") // 拦截所有 /user 开头的接口
                .excludePathPatterns("/user/login", "/user/register"); // 多个排除路径用逗号分隔

        // 管理员拦截器：拦截 /admin/** 下的接口，排除登录
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**","/essay/**","/common/**") // 拦截所有 /admin 开头的接口
                .excludePathPatterns("/admin/login");
    }
}