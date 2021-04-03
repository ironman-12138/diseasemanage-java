package com.xtn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.common.hander.BusinessException;
import com.xtn.domain.*;
import com.xtn.mapper.ConsumerMapper;
import com.xtn.mapper.OutStockInfoMapper;
import com.xtn.mapper.OutStockMapper;
import com.xtn.mapper.ProductMapper;
import com.xtn.service.OutStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.*;
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
public class OutStockServiceImpl extends ServiceImpl<OutStockMapper, OutStock> implements OutStockService {

    @Resource
    private OutStockMapper outStockMapper;
    @Resource
    private OutStockInfoMapper outStockInfoMapper;
    @Resource
    private ConsumerMapper consumerMapper;
    @Resource
    private ProductMapper productMapper;

    //分页模糊查询物资出库信息
    @Override
    public PaginationVo<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO) {
        PaginationVo<OutStockVO> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<OutStockVO> consumerList = outStockMapper.selectInStockList(outStockVO);
        //获取总记录数pageInfo.getTotal()
        PageInfo<OutStockVO> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    //分页查询物资出库明细
    @Override
    public PaginationVo<OutStockVO> selectOutStockInfo(Integer pageNum, Integer pageSize, OutStockVO outStockVO) {
        PaginationVo<OutStockVO> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<OutStockVO> consumerList = outStockMapper.selectOutStockInfo(outStockVO.getId());
        //获取总记录数pageInfo.getTotal()
        PageInfo<OutStockVO> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    //删除出库信息
    @Override
    @Transactional
    public boolean delete(OutStockVO outStockVO) {

        outStockMapper.deleteById(outStockVO.getId());
        outStockInfoMapper.delete(new QueryWrapper<OutStockInfo>().eq("out_num",outStockVO.getOutNum()));
        return true;

    }

    //修改状态
    @Override
    public boolean updateStatus(Long id, Integer status) {

        int res = outStockMapper.updateStatus(id,status);
        if (res != 0){
            return true;
        }
        return false;

    }

    //添加出库信息
    @Override
    public void addOutStock(OutStockVO outStockVO, Consumer consumer) {
        //随机生成出库订单号
        String OUT_NUM = UUID.randomUUID().toString().substring(0, 32).replace("-","");
        int itemNumber=0;//记录该单的总数
        //保存物资去处信息并获得id
        consumer.setCreateTime(new Date());
        consumerMapper.insert(consumer);
        outStockVO.setConsumerId(consumer.getId());
        //获取商品的明细
        List<Object> products = outStockVO.getProducts();
        if(!CollectionUtils.isEmpty(products)) {
            for (Object product : products) {
                LinkedHashMap item = (LinkedHashMap) product;
                //出库数量
                int productNumber = (int) item.get("productNumber");
                //物资编号
                Integer productId = (Integer) item.get("productId");
                Product dbProduct = productMapper.selectById(productId);
                if (dbProduct == null) {
                    throw new BusinessException(600,"没有物资信息");
                }else if(dbProduct.getStatus()==1) {
                    throw new BusinessException(601, dbProduct.getName() + "物资已被回收,无法出库");
                } else if(dbProduct.getStatus()==2){
                    throw new BusinessException(602, dbProduct.getName() + "物资待审核,无法出库");
                }else if(productNumber<=0){
                    throw new BusinessException(603,dbProduct.getName()+"入库数量不合法,无法出库");
                } else {
                    itemNumber += productNumber;
                    //插入出库单明细
                    OutStockInfo outStockInfo = new OutStockInfo();
                    outStockInfo.setCreateTime(new Date());
                    outStockInfo.setModifiedTime(new Date());
                    outStockInfo.setProductNumber(productNumber);
                    outStockInfo.setPNum(dbProduct.getPNum());
                    outStockInfo.setOutNum(OUT_NUM);
                    outStockInfoMapper.insert(outStockInfo);
                }
            }

            OutStock outStock = new OutStock();
            BeanUtils.copyProperties(outStockVO,outStock);
            outStock.setCreateTime(new Date());
            outStock.setProductNumber(itemNumber);
            outStock.setOperator(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            //生成入库单
            outStock.setOutNum(OUT_NUM);
            //设置为待审核
            outStock.setStatus(2);
            outStockMapper.insert(outStock);


        }else {
            throw new BusinessException(604,"出库异常");
        }


    }

    //物资出库列表
    @Override
    public List<ProductChartsVo> outProductCharts() {
        return outStockMapper.outProductCharts();
    }

    //物资出库七天统计
    @Override
    public List<SevenCountVo> outSeven() {
        return outStockMapper.outSeven();
    }


}
