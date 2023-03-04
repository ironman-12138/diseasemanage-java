package com.xtn.service.impl;

import com.xtn.domain.UserRole;
import com.xtn.mapper.UserRoleMapper;
import com.xtn.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-12-31
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
