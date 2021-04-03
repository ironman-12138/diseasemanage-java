package com.xtn.service;

import com.xtn.common.Result;
import com.xtn.domain.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    //查询所有分类树形结构
    List<ProductCategory> findAll(Long id);

    //分页查询分类列表
    PaginationVo<ProductCategory> findProductCategoryList(Integer pageNum, Integer pageSize, ProductCategory productCategory);

    //删除分类及其下所有子分类
    boolean deleteById(Long id);
}
