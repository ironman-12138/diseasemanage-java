package com.xtn.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.domain.User;
import com.xtn.mapper.UserMapper;
import com.xtn.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@Service
public class
UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    //分页查询所有用户信息
    @Override
    public Page<User> selectUsers(Integer current, Integer size) {
        Page<User> page = new Page<>(current,size);
        Page<User> userPage = userMapper.selectPage(page, Wrappers.emptyWrapper());
        return userPage;
    }
}
