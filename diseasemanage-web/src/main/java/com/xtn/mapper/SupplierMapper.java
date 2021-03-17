package com.xtn.mapper;

import com.xtn.domain.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface SupplierMapper extends BaseMapper<Supplier> {

    //分页模糊查询物资来源
    List<Supplier> selectSupplierList(Supplier supplier);
}
