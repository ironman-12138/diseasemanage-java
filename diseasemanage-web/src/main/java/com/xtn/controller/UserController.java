package com.xtn.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.User;
import com.xtn.service.UserService;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.UserDetail;
import com.xtn.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Api(tags = "系统用户模块-管理接口")
@Slf4j
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
        System.out.println(userVo);
        PaginationVo<UserDetail> userList = userService.getUserList(currentPage, pageSize, userVo);
        //获取总记录数
        long total = userList.getTotal();
        //获取数据
        List<UserDetail> records = userList.getDataList();
        return Result.ok().data("users",records).data("total",total);
    }

    /**
     * 根据id查询用户
     * @param id 用户id
     * @return
     */
    @GetMapping(value = "/selectUserById")
    @ApiOperation(value = "根据id查询用户",notes = "根据id查询用户信息")
    public Result selectUserById(Long id){
        User user = userService.getById(id);
        if (user != null){
            return Result.ok().data("user",user);
        }else {
            throw new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return
     */
    @GetMapping(value = "/selectUserByName")
    @ApiOperation(value = "根据用户名查询用户",notes = "根据用户名查询用户信息")
    public Result selectUserByName(String username){
        User user = userService.getUserByName(username);
        if (user != null){
            return Result.ok().data("user",user);
        }else {
            throw new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "添加用户",notes = "添加用户信息")
    public Result saveUser(@RequestBody User user){
        try{
            userService.addUser(user);
            return Result.ok();
        }catch (Exception e){
            log.info("用户添加失败");
            return Result.error();
        }
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value = "根据id删除用户",notes = "根据id删除用户")
    public Result delete(User user){
        boolean b = userService.removeById(user.getId());
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping(value = "/edit")
    @ApiOperation(value = "编辑用户",notes = "编辑用户")
    public Result selectById(User user){
        UserVo userVo = userService.selectById(user.getId());
        if (userVo != null){
            return Result.ok().data("user",userVo);
        }
        return Result.error();
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "更新用户",notes = "更新用户")
    public Result update(UserVo userVo){
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        boolean b = userService.updateById(user);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping(value = "/updateStatus")
    @ApiOperation(value = "修改用户状态",notes = "修改用户状态")
    public Result updateStatus(User user){
        boolean b = userService.updateStatus(user.getId(),user.getEnable());
        if (b){
            return Result.ok();
        }
        return Result.error();
    }
}

