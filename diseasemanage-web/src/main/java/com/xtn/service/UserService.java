package com.xtn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface UserService extends IService<User> {

    //分页查询所有用户信息
    public Page<User> selectUsers(Integer current, Integer size);
}
