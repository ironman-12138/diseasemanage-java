package com.xtn.mapper;

import com.xtn.domain.InStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xtn.vo.InStockVO;
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
public interface InStockMapper extends BaseMapper<InStock> {

    //分页模糊查询物资入库信息
    List<InStockVO> selectInStockList(InStockVO inStockVO);

    //分页查询物资入库明细
    List<InStockVO> selectInStockInfo(@Param("id") Long id);

    //移入回收站
    int updateStatus(@Param("id") Long id,@Param("status") Integer status);

    //物资入库列表
    List<ProductChartsVo> inProductCharts();

    //物资入库七天统计
    List<SevenCountVo> inSeven();
}
