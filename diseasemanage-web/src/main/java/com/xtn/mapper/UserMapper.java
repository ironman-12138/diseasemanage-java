package com.xtn.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.domain.Menu;
import com.xtn.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xtn.vo.UserDetail;
import com.xtn.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    IPage<User> getUserList(Page<UserDetail> page, @Param(Constants.WRAPPER) QueryWrapper<UserDetail> queryWrapper);

    //根据条件模糊分页查询用户信息
    List<UserDetail> selectUserDetailList(UserVo userVo);

    UserVo selectUserVoById(Long id);


    int updateStatus(@Param("id") Long id,@Param("enable") Integer enable);
}
