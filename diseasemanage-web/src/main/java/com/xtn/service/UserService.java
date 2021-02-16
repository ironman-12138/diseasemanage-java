package com.xtn.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xtn.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.UserVo;
import org.apache.ibatis.annotations.Param;


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
}
