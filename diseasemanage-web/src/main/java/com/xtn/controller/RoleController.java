package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.domain.Menu;
import com.xtn.domain.Role;
import com.xtn.service.MenuService;
import com.xtn.service.RoleService;
import com.xtn.vo.GrantVo;
import com.xtn.vo.PaginationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@RestController
@RequestMapping("/role")
@Api(tags = "系统角色模块-管理接口")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    /**
     * 分页模糊查询角色列表
     * @return
     */
    @ApiOperation(value = "分页模糊查询角色列表", notes = "分页模糊查询角色列表")
    @GetMapping("/findRoleList")
    public Result findRoleList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  Role role) {
        PaginationVo<Role> roleList = roleService.findRoleList(pageNum, pageSize, role);
        return Result.ok().data("role",roleList);
    }

    @ApiOperation(value = "查询角色下拉列表", notes = "查询角色下拉列表")
    @GetMapping("/downList")
    public Result downList() {
        List<Role> roleList = roleService.getDownList();
        return Result.ok().data("roleList",roleList);
    }

    /**
     * 角色拥有的菜单权限id和菜单
     * @return
     */
    @ApiOperation(value = "角色菜单")
    @PostMapping("/findRoleMenu")
    public Result findRoleMenu(Role role){
        List<Menu> tree = menuService.findAllMenuAndButton();
        //角色拥有的菜单id
        List<Long> mids = roleService.findMenuIdsByRoleId(role.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("tree", tree);
        map.put("mids", mids);
        return Result.ok().data("tree",tree).data("mids",mids);
    }

    /**
     * 角色授权
     * @return
     */
    @ApiOperation(value = "角色授权")
    @PostMapping("/authority")
    public Result authority(GrantVo grantVo){
        long[] longs = Arrays.stream(toStringArray(grantVo.getMenuIds())).mapToLong(Long::parseLong).toArray();
        boolean flag = roleService.authority(grantVo.getId(),longs);
        if (flag){
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 根据id查询角色
     * @return
     */
    @ApiOperation(value = "根据id查询角色")
    @PostMapping("/select")
    public Result select(Role role){
        Role role1 = roleService.getById(role.getId());
        if (role1 != null){
            return Result.ok().data("role",role1);
        }
        return Result.error();
    }

    /**
     * 根据id删除角色
     * @return
     */
    @ApiOperation(value = "根据id删除角色")
    @PostMapping("/delete")
    public Result delete(Role role){
        boolean b = roleService.removeById(role.getId());
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 添加角色
     * @return
     */
    @ApiOperation(value = "添加角色")
    @PostMapping("/add")
    public Result add(Role role){
        role.setCreateTime(new Date());
        boolean b = roleService.save(role);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 更新角色
     * @return
     */
    @ApiOperation(value = "更新角色")
    @PostMapping("/update")
    public Result update(Role role){
        role.setModifiedTime(new Date());
        boolean b = roleService.updateById(role);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 修改角色状态
     * @return
     */
    @ApiOperation(value = "修改角色状态")
    @PostMapping("/updateStatus")
    public Result updateStatus(Role role){
        role.setModifiedTime(new Date());
        boolean b = roleService.updateById(role);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    public static String[] toStringArray(String source) {
        return source.split(",");
    }
}

