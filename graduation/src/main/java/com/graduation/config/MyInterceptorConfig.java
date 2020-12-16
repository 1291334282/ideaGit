//package com.graduation.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class MyInterceptorConfig extends WebMvcConfigurationSupport {
//    @Value("${prop.upload-folder}")
//    private String UPLOAD_FOLDER;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////文件磁盘图片url 映射
////配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
//        registry.addResourceHandler("imgsave/images/**").addResourceLocations("file///"+ UPLOAD_FOLDER);
//        System.out.println("=============");
//       // super.addResourceHandlers(registry);
//    }
//
////
////
////    @Override
////    protected void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(jwtInterceptor)
////                .addPathPatterns("/**")
////                .excludePathPatterns("/login")
////                .excludePathPatterns("/img/**");
////    }
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/**").addResourceLocations("file:" + UPLOAD_FOLDER);
////    }
//}