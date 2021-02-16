package com.xtn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface UserMapper extends BaseMapper<User> {

    //根据条件分页查询用户信息
    IPage<User> getUserList(Page<User> page,@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);
}
