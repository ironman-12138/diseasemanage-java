package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.ProductCategory;
import com.xtn.service.ProductCategoryService;
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
@RequestMapping("/product-category")
@Api(tags = "物资类别模块-管理接口")
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "查询所有分类树形结构", notes = "查询所有物资分类树形结构")
    @GetMapping("/findAll")
    public List<ProductCategory> findAll(){
        return productCategoryService.findAll((long) 0);
    }


    @ApiOperation(value = "分页查询分类列表", notes = "物资分页查询分类列表,根据物资分类名模糊查询")
    @GetMapping("/findProductCategoryList")
    public Result findProductCategoryList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ProductCategory productCategory) {

        PaginationVo<ProductCategory> departmentsList = productCategoryService.findProductCategoryList(pageNum, pageSize, productCategory);
        return Result.ok().data("list",departmentsList);
    }

    @ApiOperation(value = "添加分类", notes = "添加物资分类")
    @PostMapping("/add")
    public Result add(ProductCategory productCategory){
        productCategory.setCreateTime(new Date());
        boolean flag = productCategoryService.save(productCategory);
        if (flag){
            return Result.ok().message("物资类别添加成功");
        }else {
            throw new BusinessException(ResultCode.ADD_ERROR.getCode(),ResultCode.ADD_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "删除分类", notes = "删除物资分类")
    @PostMapping("/delete")
    public Result delete(ProductCategory productCategory){
        boolean flag = productCategoryService.deleteById(productCategory.getId());
        if (flag){
            return Result.ok().message("删除成功");
        }else {
            throw new BusinessException(ResultCode.DEL_ERROR.getCode(),ResultCode.DEL_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "修改分类", notes = "修改物资分类")
    @PostMapping("/update")
    public Result update(ProductCategory productCategory){
        boolean flag = productCategoryService.updateById(productCategory);
        if (flag){
            return Result.ok().message("物资类别修改成功");
        }else {
            throw new BusinessException(ResultCode.ADD_ERROR.getCode(),ResultCode.ADD_ERROR.getMessage());
        }
    }

}

