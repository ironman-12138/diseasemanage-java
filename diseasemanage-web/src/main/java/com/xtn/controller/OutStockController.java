package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.*;
import com.xtn.service.OutStockService;
import com.xtn.vo.*;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/out-stock")
public class OutStockController {

    @Resource
    private OutStockService outStockService;

    @ApiOperation(value = "分页模糊查询", notes = "分页模糊查询物资出库信息")
    @GetMapping("/findOutStockList")
    public Result findInStockList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  OutStockVO outStockVO){

        PaginationVo<OutStockVO> outStockPage = outStockService.findOutStockList(pageNum, pageSize, outStockVO);

        return Result.ok().data("list",outStockPage);
    }

    @ApiOperation(value = "分页查询明细", notes = "分页查询物资出库明细")
    @PostMapping("/select")
    public Result select(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize") Integer pageSize,
                         OutStockVO outStockVO){

        PaginationVo<OutStockVO> outStockPage = outStockService.selectOutStockInfo(pageNum, pageSize, outStockVO);

        return Result.ok().data("list",outStockPage);
    }

    @ApiOperation(value = "删除出库", notes = "删除出库信息")
    @PostMapping("/delete")
    public Result delete(OutStockVO outStockVO){

        if (outStockVO.getId() == null){
            throw new BusinessException(600,"id不能为空");
        }
        if (outStockVO.getOutNum() == null){
            throw new BusinessException(601,"必传参数不能为空");
        }

        boolean flag = outStockService.delete(outStockVO);

        if (flag){
            return Result.ok().message("删除成功");
        }else {
            throw new BusinessException(ResultCode.DEL_ERROR.getCode(),ResultCode.DEL_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "修改状态", notes = "修改状态(0:正常出库,1:已进入回收,2:等待审核)")
    @PostMapping("/update")
    public Result move(OutStock outStock){

        if (outStock.getId() == null){
            throw new BusinessException(600,"id不能为空");
        }

        boolean flag = outStockService.updateStatus(outStock.getId(),outStock.getStatus());

        if (flag){
            return Result.ok().message("修改成功");
        }else {
            throw new BusinessException(ResultCode.UPDATE_ERROR.getCode(),ResultCode.UPDATE_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "物资出库", notes = "提交出库信息")
    @PostMapping("/add")
    public Result addOutStock(@RequestBody @Validated OutStockVO outStockVO) throws BusinessException {
        Consumer consumer = new Consumer();
        if(outStockVO.getConsumerId()==null){
            //说明现在添加物资来源
            BeanUtils.copyProperties(outStockVO,consumer);
            if("".equals(consumer.getName())||consumer.getName()==null){
                throw new BusinessException(500,"物资去向名不能为空");
            }
            if("".equals(consumer.getContact())||consumer.getContact()==null){
                throw new BusinessException(501,"联系人不能为空");
            }
            if("".equals(consumer.getAddress())||consumer.getAddress()==null){
                throw new BusinessException(502,"地址不能为空");
            }
            if("".equals(consumer.getPhone())||consumer.getPhone()==null){
                throw new BusinessException(503,"联系方式不能为空");
            }
            if(consumer.getSort()==null){
                throw new BusinessException(504,"排序不能为空");
            }

        }
        //提交发放单
        outStockService.addOutStock(outStockVO,consumer);
        return Result.ok();
    }

    @ApiOperation(value = "物资出库列表", notes = "绘制雷达图使用")
    @PostMapping("/outProductCharts")
    public Result outProductCharts() {
        List<ProductChartsVo> list = outStockService.outProductCharts();
        return Result.ok().data("product",list);
    }

    @ApiOperation(value = "物资出库七天统计", notes = "绘制折线图使用")
    @PostMapping("/outSeven")
    public Result outSeven() {
        List<SevenCountVo> list = outStockService.outSeven();
        return Result.ok().data("product",list);
    }

}

