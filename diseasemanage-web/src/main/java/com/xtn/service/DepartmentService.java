package com.xtn.service;

import com.xtn.domain.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
public interface DepartmentService extends IService<Department> {

    //获取所有部门和部门人数
    List<Department> getDeptAndCount();

    //分页查询部门信息
    PaginationVo<Department> findDept(Integer pageNum, Integer pageSize, Department department);
}
