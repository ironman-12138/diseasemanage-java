package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.TbUser;
import com.xtn.service.TbUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/user")
@Api(value = "系统用户模块",tags = "系统用户接口")
public class TbUserController {

    @Resource
    private TbUserService tbUserService;

    /**
     * 查询所有用户信息
     * @return Result
     */
    @GetMapping(value = "/select")
    @ApiOperation(value = "用户列表",notes = "查询所有用户信息")
    public Result selectUsers(){
        List<TbUser> userList = tbUserService.list();
        return Result.ok().data("users",userList);
    }

    @GetMapping(value = "/selectUser/{id}")
    @ApiOperation(value = "用户列表",notes = "查询所有用户信息")
    public Result selectUserById(@PathVariable("id") Long id){
        TbUser user = tbUserService.getById(id);
        if (user != null){
            return Result.ok().data("user",user);
        }else {
            throw  new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }
}

