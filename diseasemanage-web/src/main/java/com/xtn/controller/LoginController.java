package com.xtn.controller;

import com.xtn.common.Result;
import com.xtn.domain.User;
import com.xtn.service.LoginLogService;
import com.xtn.service.UserService;
import com.xtn.vo.UserLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录验证控制层
 */
@Api(tags = "登录模块-登录接口")
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody UserLogin userLogin, HttpServletRequest request){
        return userService.login(userLogin.getUsername(),userLogin.getPassword(),userLogin.getCode(),request);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/user/info")
    public User getUserInfo(Principal principal){
        if (null == principal){
            return null;
        }
        String username = principal.getName();
        User user = userService.getUserByName(username);
        user.setPassword(null);
        user.setRoles(userService.getRolesById(user.getId()));
        return user;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok().code(200).message("注销成功");
    }

}
