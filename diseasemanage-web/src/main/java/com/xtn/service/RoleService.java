package com.xtn.service;

import com.xtn.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface RoleService extends IService<Role> {

    //分页模糊查询角色列表
    PaginationVo<Role> findRoleList(Integer pageNum, Integer pageSize, Role role);

    List<Long> findMenuIdsByRoleId(Long id);

    boolean authority(Long id,long... longs);

    //根据用户id查询用户的角色id列表
    List<Long> selectRoleIdListByUserId(Long id);

    //获取全部角色列表
    List<Role> getDownList();
}
