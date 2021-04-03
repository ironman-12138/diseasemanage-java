package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.Consumer;
import com.xtn.domain.Product;
import com.xtn.mapper.ProductMapper;
import com.xtn.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.ProductStockVO;
import com.xtn.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public void add(ProductVO productVO) {
        Product product = new Product();
        BeanUtils.copyProperties(productVO,product);
        product.setCreateTime(new Date());
        product.setModifiedTime(new Date());
        @NotNull(message = "分类不能为空") Long[] categoryKeys = productVO.getCategoryKeys();
        if(categoryKeys.length==3){
            product.setOneCategoryId(categoryKeys[0]);
            product.setTwoCategoryId(categoryKeys[1]);
            product.setThreeCategoryId(categoryKeys[2]);
        }
        product.setStatus(2);//未审核
        product.setPNum(UUID.randomUUID().toString().substring(0,32));
        productMapper.insert(product);
    }

    @Override
    public PaginationVo<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PaginationVo<ProductVO> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<ProductVO> productList = productMapper.selectProductList(productVO);
        //获取总记录数pageInfo.getTotal()
        PageInfo<ProductVO> pageInfo = new PageInfo<>(productList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(productList);
        return vo;
    }

    @Override
    public Product edit(Long id) {
        Product product = productMapper.selectById(id);
        return product;
    }

    @Override
    public void updateProduct(ProductVO productVO) {

    }

    @Override
    public void delete(Long id) throws BusinessException {

    }

    @Override
    public void publish(Long id) {
        productMapper.publish(id);
    }

    //可出库物资列表
    @Override
    public PaginationVo<ProductVO> findOutProductList(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PaginationVo<ProductVO> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<ProductVO> productList = productMapper.selectOutProductList(productVO);
        //获取总记录数pageInfo.getTotal()
        PageInfo<ProductVO> pageInfo = new PageInfo<>(productList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(productList);
        return vo;
    }

    @Override
    public List<ProductChartsVo> selectAll() {
        return productMapper.selectAll();
    }


}
