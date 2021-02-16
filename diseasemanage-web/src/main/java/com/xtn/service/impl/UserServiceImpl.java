package com.xtn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.domain.User;
import com.xtn.mapper.UserMapper;
import com.xtn.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /*//分页查询所有用户信息
    @Override
    public Page<User> selectUsers(Integer current, Integer size) {
        Page<User> page = new Page<>(current,size);
        Page<User> userPage = userMapper.selectPage(page, Wrappers.emptyWrapper());
        return userPage;
    }*/

    //根据条件分页查询用户信息
    @Override
    public IPage<User> getUserList(Integer currentPage,Integer pageSize,UserVo userVo) {
        Page<User> page = new Page<>(currentPage,pageSize);
        QueryWrapper<User> queryWrapper = null;
        if (userVo != null){
            queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(userVo.getDepartmentId())){
                queryWrapper.eq("department_id",userVo.getDepartmentId());
            }
            if (!StringUtils.isEmpty(userVo.getUsername())){
                queryWrapper.eq("username",userVo.getUsername());
            }
            if (!StringUtils.isEmpty(userVo.getNickname())){
                queryWrapper.eq("nickname",userVo.getNickname());
            }
            if (!StringUtils.isEmpty(userVo.getEmail())){
                queryWrapper.eq("email",userVo.getEmail());
            }
            if (!StringUtils.isEmpty(userVo.getSex())){
                queryWrapper.eq("sex",userVo.getSex());
            }
        }
        return this.baseMapper.getUserList(page,queryWrapper);
    }
}
