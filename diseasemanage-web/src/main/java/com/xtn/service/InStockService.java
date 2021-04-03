package com.xtn.service;

import com.xtn.domain.InStock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.InStockVO;
import com.xtn.vo.PaginationVo;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.SevenCountVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface InStockService extends IService<InStock> {

    //分页模糊查询物资入库信息
    PaginationVo<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO);

    //分页查询物资入库明细
    PaginationVo<InStockVO> selectInStockInfo(Integer pageNum, Integer pageSize, InStockVO inStockVO);

    //添加入库
    void addIntoStock(InStockVO inStockVO);

    //删除入库
    boolean delete(InStockVO inStockVO);

    //修改状态
    boolean updateStatus(Long id,Integer status);

    //物资入库列表
    List<ProductChartsVo> inProductCharts();

    //物资入库七天统计
    List<SevenCountVo> inSeven();
}
