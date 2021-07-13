package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.domain.Menu;
import com.xtn.service.MenuService;
import com.xtn.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
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

    @ApiOperation(value = "查询所有菜单列表")
    @GetMapping("menuList")
    public List<Menu> getMenuList(){
        return menuService.findMenuList();
    }

    @ApiOperation(value = "根据id查询菜单项")
    @PostMapping("menu/select")
    public Result select(Menu menu){
        Menu byId = menuService.getById(menu.getId());
        if (byId != null){
            return Result.ok().data("menu",byId);
        }
        return Result.error();
    }

    @ApiOperation(value = "根据id删除菜单项")
    @PostMapping("menu/delete")
    public Result delete(Menu menu){
        boolean b = menuService.removeById(menu.getId());
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "修改菜单项")
    @PostMapping("menu/update")
    public Result update(Menu menu){
        menu.setModifiedTime(new Date());
        boolean b = menuService.updateById(menu);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "添加菜单项")
    @PostMapping("menu/add")
    public Result addParent(Menu menu){
        menu.setCreateTime(new Date());
        boolean b = menuService.save(menu);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }
}

