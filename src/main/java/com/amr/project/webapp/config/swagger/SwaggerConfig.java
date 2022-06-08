package com.amr.project.webapp.config.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis (RequestHandlerSelectors.basePackage ("com.amr.project.webapp.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getInfoAboutSwagger());
    }

    //необязательно, общая информация о документации
    private ApiInfo getInfoAboutSwagger() {
        return new ApiInfo(
                "Информация об Api проекта",
                "Описание Api проекта",
                "Version 0.1",
                "Terms of Service",
                new Contact("Group 4", "https://example.com", "example@ya.ru"),
                "Licence version 0.1",
                "https://example.com",
                new ArrayList<>()
        );
    }

}
