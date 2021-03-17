package com.xtn.service.impl;

import com.xtn.domain.Health;
import com.xtn.mapper.HealthMapper;
import com.xtn.service.HealthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
