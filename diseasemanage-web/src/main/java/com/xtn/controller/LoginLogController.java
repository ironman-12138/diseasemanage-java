package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.domain.LoginLog;
import com.xtn.service.LoginLogService;
import com.xtn.vo.InStockVO;
import com.xtn.vo.PaginationVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 登录日志表 前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/login-log")
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @ApiOperation(value = "分页模糊查询", notes = "分页模糊查询物资入库信息")
    @GetMapping("/getLoginLogList")
    public Result getLoginLogList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  LoginLog loginLog){

        PaginationVo<LoginLog> logPage = loginLogService.findLoginLogList(pageNum, pageSize, loginLog);

        return Result.ok().data("log",logPage);
    }

    @ApiOperation(value = "删除登录日志", notes = "删除登录日志")
    @GetMapping("/delete")
    public Result deleteById(LoginLog loginLog){

        boolean b = loginLogService.removeById(loginLog.getId());
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

}

