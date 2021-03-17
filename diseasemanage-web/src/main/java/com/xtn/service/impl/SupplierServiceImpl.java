package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.domain.Supplier;
import com.xtn.mapper.SupplierMapper;
import com.xtn.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.PaginationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-03-12
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    /**
     * 分页模糊查询物资来源
     * @param pageNum 查询页号
     * @param pageSize 每页条数
     * @param supplier 查询条件
     * @return 分页结果集
     */
    @Override
    public PaginationVo<Supplier> findSupplierList(Integer pageNum, Integer pageSize, Supplier supplier) {
        PaginationVo<Supplier> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<Supplier> supplierList = supplierMapper.selectSupplierList(supplier);
        //获取总记录数pageInfo.getTotal()
        PageInfo<Supplier> pageInfo = new PageInfo<>(supplierList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(supplierList);
        return vo;
    }
}
