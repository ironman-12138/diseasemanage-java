package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.domain.Consumer;
import com.xtn.mapper.ConsumerMapper;
import com.xtn.service.ConsumerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.PaginationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {

    @Resource
    private ConsumerMapper consumerMapper;

    /**
     * 添加物资去向
     * @param consumer
     */
    @Override
    public boolean add(Consumer consumer) {
        int res = consumerMapper.insert(consumer);
        if (res != 0) {
            return true;
        }
        return false;
    }

    /**
     * 分页查询物资去向列表
     * @param pageNum  页号
     * @param pageSize  每页条数
     * @param consumer
     * @return
     */
    @Override
    public PaginationVo<Consumer> findConsumerList(Integer pageNum, Integer pageSize, Consumer consumer) {
        PaginationVo<Consumer> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<Consumer> consumerList = consumerMapper.selectConsumerList(consumer);
        //获取总记录数pageInfo.getTotal()
        PageInfo<Consumer> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    /**
     * 编辑物资去向
     * @param id
     * @return
     */
    @Override
    public Consumer edit(Long id) {
        return null;
    }

    /**
     * 更新物资去向
     * @param consumer
     */
    @Override
    public void update(Consumer consumer) {
        consumerMapper.updateById(consumer);
    }


}
