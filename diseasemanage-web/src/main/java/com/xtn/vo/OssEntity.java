package com.xtn.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alioss")  //获取配置文件中alioss前缀的值赋值给下面的属性
@Component
@Data
public class OssEntity {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
