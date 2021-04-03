package com.xtn.mapper;

import com.xtn.domain.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    //分页查询分类列表
    List<ProductCategory> selectCategoryList(ProductCategory productCategory);

    //查找该id结点下所有子节点
    List<ProductCategory> getListById(@Param("id") Long id);
}
