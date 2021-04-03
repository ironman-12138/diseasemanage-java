package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.domain.Consumer;
import com.xtn.domain.Department;
import com.xtn.mapper.DepartmentMapper;
import com.xtn.service.DepartmentService;
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
 * @since 2021-02-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    //获取所有部门和部门人数
    @Override
    public List<Department> getDeptAndCount() {
        return this.baseMapper.getDeptAndCount();
    }

    //分页查询部门信息
    @Override
    public PaginationVo<Department> findDept(Integer pageNum, Integer pageSize, Department department) {
        PaginationVo<Department> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<Department> consumerList = departmentMapper.findDept(department);
        //获取总记录数pageInfo.getTotal()
        PageInfo<Department> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;

    }
}
