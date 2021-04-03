package com.xtn.mapper;

import com.xtn.domain.Health;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface HealthMapper extends BaseMapper<Health> {

    Health isReport(Long userId);

    List<Health> selectHistoryCard(Health health);
}
