package com.xtn.common.hander;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务异常类
 * 时间：2021年2月14日 21:14:25
 */

@Data
@AllArgsConstructor  //创建包含所有参数的构造函数
@NoArgsConstructor  //创建没有参数的构造函数
public class BusinessException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    Integer code;
    @ApiModelProperty(value = "错误消息")
    String errMessage;
}
