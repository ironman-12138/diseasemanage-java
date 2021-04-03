package com.xtn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.*;
import com.xtn.mapper.InStockInfoMapper;
import com.xtn.mapper.InStockMapper;
import com.xtn.mapper.ProductMapper;
import com.xtn.service.InStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.InStockVO;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.SevenCountVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
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
public class InStockServiceImpl extends ServiceImpl<InStockMapper, InStock> implements InStockService {

    @Resource
    private InStockMapper inStockMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private InStockInfoMapper inStockInfoMapper;

    //分页模糊查询物资入库信息
    @Override
    public PaginationVo<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO) {
        PaginationVo<InStockVO> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<InStockVO> consumerList = inStockMapper.selectInStockList(inStockVO);
        //获取总记录数pageInfo.getTotal()
        PageInfo<InStockVO> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    //分页查询物资入库明细
    @Override
    public PaginationVo<InStockVO> selectInStockInfo(Integer pageNum, Integer pageSize, InStockVO inStockVO) {
        PaginationVo<InStockVO> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<InStockVO> consumerList = inStockMapper.selectInStockInfo(inStockVO.getId());
        //获取总记录数pageInfo.getTotal()
        PageInfo<InStockVO> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    /**
     * 物资入库
     * @param inStockVO
     */
    @Transactional
    @Override
    public  void addIntoStock(InStockVO inStockVO){
        //随机生成入库单号
        String IN_STOCK_NUM = UUID.randomUUID().toString().substring(0, 32).replace("-","");
        int itemNumber=0;//记录该单的总数
        //获取商品的明细
        List<Object> products = inStockVO.getProducts();
        if(!CollectionUtils.isEmpty(products)) {
            for (Object product : products) {
                LinkedHashMap item = (LinkedHashMap) product;
                //入库数量
                int productNumber = (int) item.get("productNumber");
                //物资编号
                Integer productId = (Integer) item.get("productId");
                Product dbProduct = productMapper.selectById(productId);
                if (dbProduct == null) {
                    throw new BusinessException(600,"没有物资信息");
                }else if(dbProduct.getStatus()==1) {
                    throw new BusinessException(601, dbProduct.getName() + "物资已被回收,无法入库");
                } else if(dbProduct.getStatus()==2){
                    throw new BusinessException(602, dbProduct.getName() + "物资待审核,无法入库");
                }else if(productNumber<=0){
                    throw new BusinessException(603,dbProduct.getName()+"入库数量不合法,无法入库");
                } else {
                    itemNumber += productNumber;
                    //插入入库单明细
                    InStockInfo inStockInfo = new InStockInfo();
                    inStockInfo.setCreateTime(new Date());
                    inStockInfo.setModifiedTime(new Date());
                    inStockInfo.setProductNumber(productNumber);
                    inStockInfo.setPNum(dbProduct.getPNum());
                    inStockInfo.setInNum(IN_STOCK_NUM);
                    inStockInfoMapper.insert(inStockInfo);
                }
            }

            InStock inStock = new InStock();
            BeanUtils.copyProperties(inStockVO,inStock);
            inStock.setCreateTime(new Date());
            inStock.setModified(new Date());
            inStock.setProductNumber(itemNumber);
            inStock.setOperator(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            //生成入库单
            inStock.setInNum(IN_STOCK_NUM);
            //设置为待审核
            inStock.setStatus(2);
            inStockMapper.insert(inStock);
        }else {
            throw new BusinessException(604,"入库异常");
        }
    }

    //删除入库
    @Override
    @Transactional
    public boolean delete(InStockVO inStockVO) {
        inStockMapper.deleteById(inStockVO.getId());
        inStockInfoMapper.delete(new QueryWrapper<InStockInfo>().eq("in_num",inStockVO.getInNum()));
        return true;
    }

    //修改状态
    @Override
    public boolean updateStatus(Long id,Integer status) {
        int res = inStockMapper.updateStatus(id,status);
        if (res != 0){
            return true;
        }
        return false;
    }

    //物资入库列表
    @Override
    public List<ProductChartsVo> inProductCharts() {
        return inStockMapper.inProductCharts();
    }

    //物资入库七天统计
    @Override
    public List<SevenCountVo> inSeven() {
        return inStockMapper.inSeven();
    }
}
