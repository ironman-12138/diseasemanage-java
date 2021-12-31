package com.xtn.service;

import com.xtn.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface MenuService extends IService<Menu> {

    //通过用户id查询菜单列表
    List<Menu> getMenusById();

    //根据角色获取菜单列表
    List<Menu> getMenuByRole();

    //查询所有菜单
    List<Menu> findAll();

    //查询所有菜单按钮
    List<Menu> findAllMenuAndButton();

    //查询所有菜单包括不可用的
    List<Menu> findMenuList();

    //根据url查询菜单所需角色列表
    List<String> getRoleListByUrl(String url);
}
