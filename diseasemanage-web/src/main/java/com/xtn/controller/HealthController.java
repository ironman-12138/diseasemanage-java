package com.xtn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtn.common.Result;
import com.xtn.domain.Health;
import com.xtn.service.HealthService;
import com.xtn.vo.PaginationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/health")
@Api(tags = "健康模块-管理接口")
public class HealthController {

    @Resource
    private HealthService healthService;

    @ApiOperation(value = "健康打卡", notes = "健康打卡")
    @PostMapping(value = "getCard")
    public Result getCard(Health health){
        health.setCreateTime(new Date());
        boolean save = healthService.save(health);
        if (save){
            return Result.ok().message("打卡成功");
        }
        return Result.error().message("打卡失败");
    }

    @ApiOperation(value = "是否打卡", notes = "是否打卡")
    @PostMapping(value = "isReport")
    public Result isReport(Health health){
        Health health1 = healthService.isReport(health.getUserId());
        if (health1.getId() != null){
            return Result.ok().code(20000).data("health",health1);
        }else {
            return Result.ok().code(40000);
        }
    }

    @ApiOperation(value = "历史打卡记录", notes = "历史打卡记录")
    @PostMapping(value = "historyCard")
    public Result historyCard(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize") Integer pageSize,
                              Health health){
        PaginationVo<Health> healthList = healthService.historyCard(pageNum,pageSize,health);
        return Result.ok().data("list",healthList);
    }

}

