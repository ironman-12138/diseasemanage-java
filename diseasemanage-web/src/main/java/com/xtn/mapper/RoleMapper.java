package com.xtn.mapper;

import com.xtn.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
}
