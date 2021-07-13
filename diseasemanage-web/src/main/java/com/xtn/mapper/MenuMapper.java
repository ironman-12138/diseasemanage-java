package com.xtn.mapper;

import com.xtn.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface MenuMapper extends BaseMapper<Menu> {

    //通过用户id查询菜单列表
    List<Menu> getMenusById(Long id);

    //根据角色获取菜单列表
    List<Menu> getMenuByRole();

    //根据用户id查询所有菜单
    List<Menu> findTree(Long id);

    //查询出所有的菜单
    List<Menu> findAll(Long id);

    List<Menu> findAllMenuAndButton(Long id);

    List<Menu> findMenuList();
}
