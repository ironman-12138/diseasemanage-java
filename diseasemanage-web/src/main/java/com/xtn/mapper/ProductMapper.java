package com.xtn.mapper;

import com.xtn.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.ProductVO;
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
public interface ProductMapper extends BaseMapper<Product> {

    List<ProductVO> selectProductList(ProductVO productVO);

    void publish(@Param("id") Long id);

    //可出库物资列表
    List<ProductVO> selectOutProductList(ProductVO productVO);

    List<ProductChartsVo> selectAll();
}
