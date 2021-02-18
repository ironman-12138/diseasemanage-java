package com.xtn.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Department;
import com.xtn.domain.User;
import com.xtn.mapper.DepartmentMapper;
import com.xtn.mapper.UserMapper;
import com.xtn.service.UserService;
import com.xtn.vo.UserVo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource PasswordEncoder passwordEncoder;

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

    //添加新用户
    @Override
    public void addUser(User user) {
        //判断用户是否重复
        String username = user.getUsername();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if (count != 0){
            //用户已存在
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS.getCode(), ResultCode.USER_ALREADY_EXISTS.getMessage());
        }

        //判断部门是否正确
        Long departmentId = user.getDepartmentId();
        Department department = departmentMapper.selectById(departmentId);
        if (department == null){
            //部门不存在
            throw new BusinessException(ResultCode.DEPART_NO_EXISTS.getCode(),ResultCode.DEPART_NO_EXISTS.getMessage());
        }
        String salt = UUID.randomUUID().toString().substring(0,32);
        user.setSalt(salt);
        //使用spring security自带的加密策略生成密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setType(1);
        user.setStatus(1);

        this.baseMapper.insert(user);
    }
}
