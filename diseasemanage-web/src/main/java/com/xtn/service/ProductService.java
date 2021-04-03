package com.xtn.service;

import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.ProductStockVO;
import com.xtn.vo.ProductVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface ProductService extends IService<Product> {

    /**
     * 添加商品
     * @param productVO
     */
    void add(ProductVO productVO);


    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PaginationVo<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO);


    /**
     * 编辑商品
     * @param id
     * @return
     */
    Product edit(Long id);

    /**
     * 更新商品
     * @param productVO
     */
    void updateProduct(ProductVO productVO);

    /**
     * 删除商品
     * @param id
     */
    void delete(Long id) throws BusinessException;

    void publish(Long id);

    /**
     * 可出库物资列表
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PaginationVo<ProductVO> findOutProductList(Integer pageNum, Integer pageSize, ProductVO productVO);


    List<ProductChartsVo> selectAll();
}
