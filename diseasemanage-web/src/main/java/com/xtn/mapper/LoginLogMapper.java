package com.xtn.mapper;

import com.xtn.domain.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 登录日志表 Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-04-04
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    List<LoginLog> selectLogList(LoginLog loginLog);
}
