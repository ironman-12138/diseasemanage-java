package com.xtn.service;

import com.xtn.domain.Consumer;
import com.xtn.domain.OutStock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.OutStockVO;
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
public interface OutStockService extends IService<OutStock> {

    //分页模糊查询物资出库信息
    PaginationVo<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO);

    //分页查询物资出库明细
    PaginationVo<OutStockVO> selectOutStockInfo(Integer pageNum, Integer pageSize, OutStockVO outStockVO);

    //删除出库信息
    boolean delete(OutStockVO outStockVO);

    //修改状态
    boolean updateStatus(Long id, Integer status);

    //添加出库信息
    void addOutStock(OutStockVO outStockVO, Consumer consumer);

    //物资出库列表
    List<ProductChartsVo> outProductCharts();

    //物资出库七天统计
    List<SevenCountVo> outSeven();
}
