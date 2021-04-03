package com.xtn.controller;


import com.xtn.domain.Menu;
import com.xtn.service.MenuService;
import com.xtn.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@Api(tags = "权限模块-权限菜单控制")
@RestController
@RequestMapping("/system")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("menus")
    public List<Menu> getMenusById(){
        return menuService.findAll();
    }

}

