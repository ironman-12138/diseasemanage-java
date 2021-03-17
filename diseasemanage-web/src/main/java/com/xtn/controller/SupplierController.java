package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.domain.Supplier;
import com.xtn.service.SupplierService;
import com.xtn.vo.PaginationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@Api(tags = "业务模块-物资来源相关接口")
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @ApiOperation(value = "模糊分页查询来源列表", notes = "模糊查询物资来源列表")
    @PostMapping("/findSupplierList")
    public Result findSupplierList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize") Integer pageSize,
                                   @RequestBody Supplier supplier) {
        PaginationVo<Supplier> supplierPage = supplierService.findSupplierList(pageNum, pageSize, supplier);
        return Result.ok().data("supplierPage",supplierPage);
    }

}

