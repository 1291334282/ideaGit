package com.graduation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class swaggerConfig {
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.graduation.controller")).build();
		
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("叼大不怕看", null, null);
		return new ApiInfo("issue管理系统接口", "不说了，闪电五连鞭", "v1.0", null, contact, null,
				null, new ArrayList());
	}
}
