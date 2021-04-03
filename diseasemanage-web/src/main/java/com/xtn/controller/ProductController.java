package com.xtn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xtn.common.Result;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Product;
import com.xtn.service.ProductService;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.ProductStockVO;
import com.xtn.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/product")
@Api(tags = "物资详情模块-管理接口")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 全部物资列表
     * @return
     */
    @ApiOperation(value = "物资列表", notes = "物资列表,模糊条件查询")
    @GetMapping("/findProductList")
    public Result findProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  @RequestParam(value = "categorys", required = false) String categorys,
                                  ProductVO productVO) {
        buildCategorySearch(categorys, productVO);
        PaginationVo<ProductVO> productVOPageVO = productService.findProductList(pageNum, pageSize, productVO);
        return Result.ok().data("product",productVOPageVO);
    }

    /**
     * 可出库物资列表
     * @return
     */
    @ApiOperation(value = "物资列表", notes = "可出库物资列表,模糊条件查询")
    @GetMapping("/findOutProductList")
    public Result findOutProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  @RequestParam(value = "categorys", required = false) String categorys,
                                  ProductVO productVO) {
        buildCategorySearch(categorys, productVO);
        PaginationVo<ProductVO> productVOPageVO = productService.findOutProductList(pageNum, pageSize, productVO);
        return Result.ok().data("product",productVOPageVO);
    }

    /**
     * 可出库物资列表
     * @return
     */
    @ApiOperation(value = "所有物资", notes = "查询所有物资列表,Echarts饼图绘制")
    @GetMapping("/findAllCharts")
    public Result findAllCharts() {
        List<ProductChartsVo> list = productService.selectAll();
        return Result.ok().data("product",list);
    }

    /**
     * 添加物资
     * @return
     */
    @ApiOperation(value = "添加物资")
    @PostMapping("/add")
    public Result add(@RequestBody ProductVO productVO) throws BusinessException {
        if (productVO.getCategoryKeys().length != 3) {
            throw new BusinessException(400,"物资需要3级分类");
        }
        productService.add(productVO);
        return Result.ok();
    }

    /**
     * 查询编辑的物资
     * @return
     */
    @ApiOperation(value = "编辑物资", notes = "编辑物资信息")
    @PostMapping("/select")
    public Result edit(ProductVO productVO) {
        Product product = productService.edit(productVO.getId());
        return Result.ok().data("product",product);
    }

    /**
     * 更新物资
     *
     * @return
     */
    @ApiOperation(value = "更新物资", notes = "更新物资信息")
    @PostMapping("/update")
    public Result update(@RequestBody Product product) throws BusinessException {
        productService.updateById(product);
        return Result.ok();
    }

    /**
     * 删除物资
     * @return
     */
    @ApiOperation(value = "删除物资", notes = "删除物资信息")
    @PostMapping("/delete")
    public Result delete(Product product) throws BusinessException {
        productService.removeById(product.getId());
        return Result.ok();
    }

    /**
     * 过审物资
     * @return
     */
    @ApiOperation(value = "过审物资", notes = "过审物资")
    @PostMapping("/publish")
    public Result publish(Product product) throws BusinessException {
        productService.publish(product.getId());
        return Result.ok();
    }

    /**
     * 封装物资查询条件
     * @param categorys
     * @param productVO
     */
    private void buildCategorySearch(@RequestParam(value = "categorys", required = false) String categorys, ProductVO productVO) {
        if (categorys != null && !"".equals(categorys)) {
            String[] split = categorys.split(",");
            switch (split.length) {
                case 1:
                    productVO.setOneCategoryId(Long.parseLong(split[0]));
                    break;
                case 2:
                    productVO.setOneCategoryId(Long.parseLong(split[0]));
                    productVO.setTwoCategoryId(Long.parseLong(split[1]));
                    break;
                case 3:
                    productVO.setOneCategoryId(Long.parseLong(split[0]));
                    productVO.setTwoCategoryId(Long.parseLong(split[1]));
                    productVO.setThreeCategoryId(Long.parseLong(split[2]));
                    break;
            }
        }
    }


}

