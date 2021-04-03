package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Supplier;
import com.xtn.service.SupplierService;
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
 * @since 2021-03-12
 */
@RestController
@RequestMapping("/supplier")
@Api(tags = "业务模块-物资来源管理接口")
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @ApiOperation(value = "模糊分页查询来源列表", notes = "模糊查询物资来源列表")
    @PostMapping("/findSupplierList")
    public Result findSupplier(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize") Integer pageSize,
                                   @RequestBody Supplier supplier) {
        PaginationVo<Supplier> supplierPage = supplierService.findSupplierList(pageNum, pageSize, supplier);
        return Result.ok().data("supplierPage",supplierPage);
    }

    @ApiOperation(value = "添加物资来源", notes = "添加物资来源")
    @PostMapping("/add")
    public Result saveSupplierList(@RequestBody Supplier supplier) {
        supplier.setCreateTime(new Date());
        boolean flag = supplierService.save(supplier);
        if (flag){
            return Result.ok();
        }else {
            throw new BusinessException(ResultCode.ADD_ERROR.getCode(),ResultCode.ADD_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "删除物资来源", notes = "删除物资来源")
    @PostMapping("/delete")
    public Result deleteSupplier(@RequestBody Supplier supplier) {
        boolean flag = supplierService.removeById(supplier.getId());
        if (flag){
            return Result.ok();
        }else {
            throw new BusinessException(ResultCode.DEL_ERROR.getCode(),ResultCode.DEL_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "更新物资来源", notes = "更新物资来源")
    @PostMapping("/update")
    public Result updateSupplier(@RequestBody Supplier supplier) {
        supplier.setModifiedTime(new Date());
        boolean flag = supplierService.updateById(supplier);
        if (flag){
            return Result.ok();
        }else {
            throw new BusinessException(ResultCode.UPDATE_ERROR.getCode(),ResultCode.UPDATE_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "根据id查询物资来源", notes = "根据id查询物资来源")
    @PostMapping("/select")
    public Result getSupplierById(@RequestBody Supplier supplier) {
        Supplier supplier1 = supplierService.getById(supplier.getId());
        if (supplier1 != null){
            return Result.ok().data("supplier",supplier1);
        }else {
            throw new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "获取所有来源", notes = "获取所有来源")
    @PostMapping("/findAll")
    public Result findAll() {
        List<Supplier> list = supplierService.list();
        if (list.size() != 0){
            return Result.ok().data("list",list);
        }else {
            throw new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }

}

