package com.xtn.mapper;

import com.xtn.domain.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    //获取所有部门和部门人数
    List<Department> getDeptAndCount();
}
