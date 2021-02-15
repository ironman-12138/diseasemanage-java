package com.xtn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springboot启动类
 * 时间：2021年2月14日 14:48:58
 */

@SpringBootApplication
@MapperScan("com.xtn.mapper")
@EnableSwagger2  //开启swaggerUi
public class DiseaseManageWeb8081 {
    public static void main(String[] args) {
        SpringApplication.run(DiseaseManageWeb8081.class,args);
    }
}
