package com.xtn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Consumer;
import com.xtn.domain.InStock;
import com.xtn.domain.ProductCategory;
import com.xtn.domain.Supplier;
import com.xtn.service.InStockService;
import com.xtn.service.SupplierService;
import com.xtn.vo.InStockVO;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.SevenCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/in-stock")
@Api(tags = "物资入库模块-管理接口")
public class InStockController {

    @Resource
    private InStockService inStockService;
    @Resource
    private SupplierService supplierService;

    @ApiOperation(value = "分页模糊查询", notes = "分页模糊查询物资入库信息")
    @GetMapping("/findInStockList")
    public Result findInStockList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  InStockVO inStockVO){

        PaginationVo<InStockVO> inStockPage = inStockService.findInStockList(pageNum, pageSize, inStockVO);

        return Result.ok().data("list",inStockPage);
    }

    @ApiOperation(value = "分页查询明细", notes = "分页查询物资入库明细")
    @PostMapping("/select")
    public Result select(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize") Integer pageSize,
                          InStockVO inStockVO){

        PaginationVo<InStockVO> inStockPage = inStockService.selectInStockInfo(pageNum, pageSize, inStockVO);

        return Result.ok().data("list",inStockPage);
    }

    @ApiOperation(value = "物资入库", notes = "物资入库")
    @PostMapping("/add")
    public Result add(@RequestBody InStockVO inStockVO){

        if(inStockVO.getSupplierId()==null){
            //说明现在添加物资来源
            Supplier supplier = new Supplier();
            BeanUtils.copyProperties(inStockVO,supplier);
            if("".equals(supplier.getName())||supplier.getName()==null){
                throw new BusinessException(500,"物资提供方名不能为空");
            }
            if("".equals(supplier.getEmail())||supplier.getEmail()==null){
                throw new BusinessException(501,"邮箱不能为空");
            }
            if("".equals(supplier.getContact())||supplier.getContact()==null){
                throw new BusinessException(502,"联系人不能为空");
            }
            if("".equals(supplier.getAddress())||supplier.getAddress()==null){
                throw new BusinessException(503,"地址不能为空");
            }
            if("".equals(supplier.getPhone())||supplier.getPhone()==null){
                throw new BusinessException(504,"联系方式不能为空");
            }
            if(supplier.getSort()==null){
                throw new BusinessException(505,"排序不能为空");
            }
            supplier.setCreateTime(new Date());
            boolean save = supplierService.save(supplier);
            System.out.println("InStockController(91)-------------->" + supplier.getId());
            inStockVO.setSupplierId(supplier.getId());
        }

        inStockService.addIntoStock(inStockVO);
        return Result.ok().message("添加成功");
    }

    @ApiOperation(value = "删除入库", notes = "删除入库信息")
    @PostMapping("/delete")
    public Result delete(InStockVO inStockVO){

        if (inStockVO.getId() == null){
            throw new BusinessException(600,"id不能为空");
        }
        if (inStockVO.getInNum() == null){
            throw new BusinessException(601,"必传参数不能为空");
        }

        boolean flag = inStockService.delete(inStockVO);

        if (flag){
            return Result.ok().message("删除成功");
        }else {
            throw new BusinessException(ResultCode.DEL_ERROR.getCode(),ResultCode.DEL_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "修改状态", notes = "修改状态(0:正常入库单,1:已进入回收,2:等待审核)")
    @PostMapping("/update")
    public Result move(InStock inStock){

        if (inStock.getId() == null){
            throw new BusinessException(600,"id不能为空");
        }

        boolean flag = inStockService.updateStatus(inStock.getId(),inStock.getStatus());

        if (flag){
            return Result.ok().message("修改成功");
        }else {
            throw new BusinessException(ResultCode.UPDATE_ERROR.getCode(),ResultCode.UPDATE_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "物资入库列表", notes = "绘制雷达图使用")
    @PostMapping("/inProductCharts")
    public Result inProductCharts() {
        List<ProductChartsVo> list = inStockService.inProductCharts();
        return Result.ok().data("product",list);
    }

    @ApiOperation(value = "物资入库七天统计", notes = "绘制柱状图使用")
    @PostMapping("/inSeven")
    public Result inSeven() {
        List<SevenCountVo> list = inStockService.inSeven();
        return Result.ok().data("product",list);
    }

}

