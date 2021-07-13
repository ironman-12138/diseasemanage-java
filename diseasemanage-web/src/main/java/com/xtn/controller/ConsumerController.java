package com.xtn.controller;


import com.xtn.common.Result;
import com.xtn.common.ResultCode;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Consumer;
import com.xtn.service.ConsumerService;
import com.xtn.vo.PaginationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 物资去向管理
 *
 * @Author xcoder
 * @Date 2020/3/16 20:18
 * @Version 1.0
 **/
@Api(tags = "业务模块-物资去向管理接口")
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    /**
     * 模糊分页查询去向列表
     *
     * @return
     */
    @ApiOperation(value = "模糊分页查询物资去向列表", notes = "去向列表,根据去向名模糊查询")
    @PostMapping("/findConsumerList")
    public Result findConsumerList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize") Integer pageSize,
                                   @RequestBody Consumer customer) {
        PaginationVo<Consumer> consumerPage = consumerService.findConsumerList(pageNum, pageSize, customer);
        return Result.ok().data("consumerPage",consumerPage);
    }

    /**
     * 添加去向
     * @return
     */
    @ApiOperation(value = "添加物资去向")
    @PostMapping("/add")
    public Result add(@RequestBody @Validated Consumer consumer) {
        consumer.setCreateTime(new Date());
        boolean flag = consumerService.add(consumer);
        if (flag){
            return Result.ok();
        }else {
            throw new BusinessException(ResultCode.ADD_ERROR.getCode(),ResultCode.ADD_ERROR.getMessage());
        }
    }

    /**
     * 根据id查询去向
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询物资去向", notes = "根据id查询去向")
    @GetMapping("/edit")
    public Result edit(Long id) {
        Consumer consumer = consumerService.edit(id);
        return Result.ok().data("consumer",consumer);
    }

    /**
     * 更新去向
     * @return
     */
    @ApiOperation(value = "更新物资去向", notes = "更新去向信息")
    @PostMapping("/update")
    public Result update(@RequestBody @Validated Consumer consumer) {
        consumer.setModifiedTime(new Date());
        boolean flag = consumerService.updateById(consumer);
        if (flag){
            return Result.ok();
        }else {
            throw new BusinessException(ResultCode.UPDATE_ERROR.getCode(),ResultCode.UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 删除去向
     * @return
     */
    @ApiOperation(value = "删除物资去向", notes = "删除去向信息")
    @PostMapping("/delete")
    public Result delete(@RequestBody Consumer consumer) {
        boolean flag = consumerService.removeById(consumer.getId());
        if (flag){
            return Result.ok();
        }else {
            throw new BusinessException(ResultCode.DEL_ERROR.getCode(),ResultCode.DEL_ERROR.getMessage());
        }
    }

    /**
     * 所有去向
     * @return
     */
    @ApiOperation(value = "查询所有物资去向", notes = "所有去向列表")
    @GetMapping("/findAll")
    public Result findAll() {
        List<Consumer> consumers = consumerService.list();
        return Result.ok().data("consumers",consumers);
    }

    @ApiOperation(value = "根据id查询去向", notes = "根据id查询去向")
    @PostMapping("/select")
    public Result selectById(@RequestBody Consumer c) {
        Consumer consumer = consumerService.getById(c.getId());
        if (consumer != null){
            return Result.ok().data("consumer",consumer);
        }else {
            throw new BusinessException(ResultCode.DATA_ERROR.getCode(),ResultCode.DATA_ERROR.getMessage());
        }
    }
}

