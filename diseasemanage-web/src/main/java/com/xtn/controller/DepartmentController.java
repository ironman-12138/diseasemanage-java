package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Consumer;
import com.xtn.domain.Department;
import com.xtn.service.DepartmentService;
import com.xtn.vo.PaginationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@RestController
@RequestMapping("/department")
@Api(tags = "权限模块-部门管理接口")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    /**
     * 获取所有部门和部门人数
     * @return
     */
    @GetMapping(value = "/selectAll")
    @ApiOperation(value = "查询部门及人数",notes = "查询部门列表和对应的人数")
    public Result getDeptAndCount(){
        List<Department> deptAndCount = departmentService.getDeptAndCount();
        if (deptAndCount.size() == 0){
            throw new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
        return Result.ok().data("dept",deptAndCount);
    }

    /**
     * 分页查询部门信息
     * @return
     */
    @PostMapping(value = "/findDept")
    @ApiOperation(value = "分页查询部门信息",notes = "分页查询部门信息")
    public Result findDept(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize") Integer pageSize,
                           Department department){
        PaginationVo<Department> departmentPage = departmentService.findDept(pageNum, pageSize, department);
        return Result.ok().data("department",departmentPage);
    }

    /**
     * 添加部门
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加部门",notes = "添加部门")
    public Result add(Department department){
        department.setCreateTime(new Date());
        boolean save = departmentService.save(department);
        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 根据id查询部门
     * @return
     */
    @PostMapping(value = "/select")
    @ApiOperation(value = "根据id查询部门",notes = "根据id查询部门")
    public Result select(Department department){
        Department department1 = departmentService.getById(department.getId());
        return Result.ok().data("dept",department1);
    }

    /**
     * 根据id删除部门
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "根据id删除部门",notes = "根据id删除部门")
    public Result delete(Department department){
        boolean b = departmentService.removeById(department.getId());
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 更新部门信息
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "更新部门信息",notes = "更新部门信息")
    public Result update(Department department){
        department.setModifiedTime(new Date());
        boolean b = departmentService.updateById(department);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }
}

