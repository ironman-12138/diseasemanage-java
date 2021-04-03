package com.xtn.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * 七天统计
 */
@Data
public class SevenCountVo {

    private Date days;

    private Integer count;

}
