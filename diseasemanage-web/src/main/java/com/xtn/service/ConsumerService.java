package com.xtn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xtn.domain.Consumer;
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
public interface ConsumerService extends IService<Consumer> {

    /**
     * 添加物资去向
     * @param consumer
     */
    boolean add(Consumer consumer);


    /**
     * 分页物资去向列表
     * @param pageNum  页号
     * @param pageSize  每页条数
     * @param consumer
     * @return
     */
    PaginationVo<Consumer> findConsumerList(Integer pageNum, Integer pageSize, Consumer consumer);


    /**
     * 编辑物资去向
     * @param id
     * @return
     */
    Consumer edit(Long id);

    /**
     * 更新物资去向
     * @param consumer
     */
    void update(Consumer consumer);


}
