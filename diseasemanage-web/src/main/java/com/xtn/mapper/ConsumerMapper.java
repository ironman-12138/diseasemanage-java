package com.xtn.mapper;

import com.xtn.domain.Consumer;
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
public interface ConsumerMapper extends BaseMapper<Consumer> {

    //查询物资去向列表
    List<Consumer> selectConsumerList(Consumer consumer);
}
