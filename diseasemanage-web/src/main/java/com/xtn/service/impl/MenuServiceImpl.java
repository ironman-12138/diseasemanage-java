package com.xtn.service.impl;

import com.xtn.domain.Menu;
import com.xtn.domain.User;
import com.xtn.mapper.MenuMapper;
import com.xtn.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    //通过用户id查询菜单列表
    @Override
    public List<Menu> getMenusById() {
        return menuMapper.getMenusById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    //根据角色获取菜单列表
    @Override
    public List<Menu> getMenuByRole() {
        return menuMapper.getMenuByRole();
    }

    //查询所有菜单
    @Override
    public List<Menu> findAll() {
        try {
            //查询出所有的菜单
            List<Menu> allMenu = menuMapper.findAll(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());

            //根节点存储
            List<Menu> rootMenu = new ArrayList<>();

            //父节点是0的，为根节点
            for (Menu nav : allMenu) {
                if(nav.getParentId().longValue() == 0){
                    rootMenu.add(nav);
                }
            }

            // 根据Menu类的order排序
            Collections.sort(rootMenu);

            //为根节点设置子菜单，getChild是递归调用
            for (Menu nv : rootMenu) {
                //获取根节点下的所有子节点，使用getChild方法
                List<Menu> childList = getChild(nv.getId(),allMenu);
                //给根节点设置子节点
                nv.setChildren(childList);
            }

            return rootMenu;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //查询出所有的菜单和按钮
    @Override
    public List<Menu> findAllMenuAndButton() {
        //查询出所有的菜单和按钮
        List<Menu> allMenu = menuMapper.findAllMenuAndButton(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        //根节点存储
        List<Menu> rootMenu = new ArrayList<>();

        //父节点是0的，为根节点
        for (Menu nav : allMenu) {
            if(nav.getParentId().longValue() == 0){
                rootMenu.add(nav);
            }
        }

        // 根据Menu类的order排序
        Collections.sort(rootMenu);

        //为根节点设置子菜单，getChild是递归调用
        for (Menu nv : rootMenu) {
            //获取根节点下的所有子节点，使用getChild方法
            List<Menu> childList = getChild(nv.getId(),allMenu);
            //给根节点设置子节点
            nv.setChildren(childList);
        }
        return rootMenu;
    }

    @Override
    public List<Menu> findMenuList() {

        //查询出所有的菜单和按钮
        List<Menu> allMenu = menuMapper.findMenuList();
        //根节点存储
        List<Menu> rootMenu = new ArrayList<>();

        //父节点是0的，为根节点
        for (Menu nav : allMenu) {
            if(nav.getParentId().longValue() == 0){
                rootMenu.add(nav);
            }
        }

        // 根据Menu类的order排序
        Collections.sort(rootMenu);

        //为根节点设置子菜单，getChild是递归调用
        for (Menu nv : rootMenu) {
            //获取根节点下的所有子节点，使用getChild方法
            List<Menu> childList = getChild(nv.getId(),allMenu);
            //给根节点设置子节点
            nv.setChildren(childList);
        }
        return rootMenu;

    }

    private List<Menu> getChild(Long id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu nav : allMenu) {
            //遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点
            if(nav.getParentId().longValue() == id.longValue()){
                childList.add(nav);
            }
        }
        //递归设置子节点
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getId(),allMenu));
        }
        //排序
        Collections.sort(childList);
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }


}
