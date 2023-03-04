package com.xtn.mapper;

import com.xtn.domain.Menu;
import com.xtn.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface RoleMapper extends BaseMapper<Role> {

    //根据用户id查询角色列表
    List<Role> getRolesById(@Param("id") Long id);

    //分页模糊查询角色列表
    List<Role> selectRoleList(Role role);

    //获取角色权限id列表
    List<Menu> findMenuIdsByRoleId(Long id);

    void remove(Long id);

    void authority(@Param("roleId") Long id,@Param("menuId") long aLong);

    //根据用户id查询用户的角色id列表
    List<Long> selectRoleIdListByUserId(Long id);
}
