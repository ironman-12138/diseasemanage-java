package com.xtn.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.User;
import com.xtn.service.UserService;
import com.xtn.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@RestController
@RequestMapping("/user")
@Api(value = "系统用户模块",tags = "系统用户接口")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据条件分页查询所有用户信息
     * @return Result
     */
    @PostMapping(value = "/select")
    @ApiOperation(value = "用户列表",notes = "根据条件分页查询所有用户信息")
    public Result selectUsers(@RequestParam(required = true,defaultValue = "1") Integer currentPage,
                              @RequestParam(required = true,defaultValue = "6") Integer pageSize,
                              @RequestBody UserVo userVo){

        IPage<User> userList = userService.getUserList(currentPage, pageSize, userVo);
        //获取总记录数
        long total = userList.getTotal();
        //获取数据
        List<User> records = userList.getRecords();
        return Result.ok().data("users",records).data("total",total);
    }

    @GetMapping(value = "/selectUser/{id}")
    @ApiOperation(value = "查询指定用户",notes = "根据id查询用户信息")
    public Result selectUserById(@PathVariable("id") Long id){
        User user = userService.getById(id);
        if (user != null){
            return Result.ok().data("user",user);
        }else {
            throw  new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }
}
