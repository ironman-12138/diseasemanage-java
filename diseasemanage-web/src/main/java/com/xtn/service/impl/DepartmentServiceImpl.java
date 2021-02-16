package com.xtn.service.impl;

import com.xtn.domain.Department;
import com.xtn.mapper.DepartmentMapper;
import com.xtn.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    //获取所有部门和部门人数
    @Override
    public List<Department> getDeptAndCount() {
        return this.baseMapper.getDeptAndCount();
    }
}
