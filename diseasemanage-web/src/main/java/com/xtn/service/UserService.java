package com.xtn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.common.Result;
import com.xtn.domain.User;
import com.xtn.vo.UserVo;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface UserService extends IService<User> {

    //根据条件分页查询用户信息
    IPage<User> getUserList(Integer currentPage,Integer pageSize,UserVo userVo);

    //添加新用户
    void addUser(User user);

    //验证用户登录
    Result login(String username, String password, String code, HttpServletRequest request);

    //根据用户名获取用户
    User getUserByName(String username);
}
