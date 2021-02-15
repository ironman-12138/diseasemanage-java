package com.xtn.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) .apiInfo(apiInfo()) .select()
        //这里一定要标注你控制器的位置
        .apis(RequestHandlerSelectors.basePackage("com.xtn.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("疫情物资管理系统API文档")
                .description("疫情物资管理系统API文档")
                .termsOfServiceUrl("https://github.com/ironman-12138")
                .contact("1481806085@qq.com")
                .version("1.0")
                .build();
    }
}
