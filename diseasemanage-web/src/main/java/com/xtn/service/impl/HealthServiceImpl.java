package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.domain.Consumer;
import com.xtn.domain.Health;
import com.xtn.mapper.HealthMapper;
import com.xtn.service.HealthService;
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
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {

    @Resource
    private HealthMapper healthMapper;

    //判断今日是否已打卡
    @Override
    public Health isReport(Long userId) {
        return healthMapper.isReport(userId);
    }

    //历史打卡记录
    @Override
    public PaginationVo<Health> historyCard(Integer pageNum, Integer pageSize, Health health) {
        PaginationVo<Health> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<Health> consumerList = healthMapper.selectHistoryCard(health);
        //获取总记录数pageInfo.getTotal()
        PageInfo<Health> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

}
