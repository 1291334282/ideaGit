//package com.graduation.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class CrosConfig implements WebMvcConfigurer {
//    @Resource
//    private StatelessAuthcFilter corsInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 跨域拦截器需放在最上面
//        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
//
//    }
//}
