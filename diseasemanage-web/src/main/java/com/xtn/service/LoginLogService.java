package com.xtn.service;

import com.xtn.domain.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-04-04
 */
public interface LoginLogService extends IService<LoginLog> {

    //记录登录日志
    void add(String username, HttpServletRequest request);

    PaginationVo<LoginLog> findLoginLogList(Integer pageNum, Integer pageSize, LoginLog loginLog);
}
