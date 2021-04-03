package com.xtn.service;

import com.xtn.domain.Health;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface HealthService extends IService<Health> {

    //判断今日是否已打卡
    Health isReport(Long userId);

    //历史打卡记录
    PaginationVo<Health> historyCard(Integer pageNum, Integer pageSize, Health health);

}
