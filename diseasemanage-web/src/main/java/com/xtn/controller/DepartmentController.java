package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Department;
import com.xtn.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@Api(value = "部门管理")
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
}

