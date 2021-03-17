package com.xtn.service;

import com.xtn.domain.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xtn.vo.PaginationVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
public interface SupplierService extends IService<Supplier> {

    /**
     * 分页模糊查询
     * @param pageNum
     * @param pageSize
     * @param supplier
     * @return
     */
    PaginationVo<Supplier> findSupplierList(Integer pageNum, Integer pageSize, Supplier supplier);
}
