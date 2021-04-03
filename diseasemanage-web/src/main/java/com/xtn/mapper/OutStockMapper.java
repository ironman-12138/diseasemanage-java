package com.xtn.mapper;

import com.xtn.domain.OutStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xtn.vo.OutStockVO;
import com.xtn.vo.ProductChartsVo;
import com.xtn.vo.SevenCountVo;
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
public interface OutStockMapper extends BaseMapper<OutStock> {

    //分页模糊查询物资出库信息
    List<OutStockVO> selectInStockList(OutStockVO outStockVO);

    //分页查询物资出库明细
    List<OutStockVO> selectOutStockInfo(@Param("id") Long id);

    //修改状态
    int updateStatus(Long id, Integer status);

    //物资出库列表
    List<ProductChartsVo> outProductCharts();

    //物资出库七天统计
    List<SevenCountVo> outSeven();
}
