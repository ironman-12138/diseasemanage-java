package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.domain.Consumer;
import com.xtn.domain.Menu;
import com.xtn.domain.ProductCategory;
import com.xtn.domain.User;
import com.xtn.mapper.ProductCategoryMapper;
import com.xtn.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.PaginationVo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    //查询所有分类树形结构
    @Override
    public List<ProductCategory> findAll(Long id) {
        try {
            //查询出所有的类别
            List<ProductCategory> allProductCategory = productCategoryMapper.selectList(null);

            //根节点存储
            List<ProductCategory> rootProductCategory = new ArrayList<>();

            //父节点是0的，为根节点
            for (ProductCategory nav : allProductCategory) {
                if(nav.getPid().longValue() == id){
                    rootProductCategory.add(nav);
                }
            }


            //为根节点设置子类别，getChild是递归调用
            for (ProductCategory nv : rootProductCategory) {
                //获取根节点下的所有子节点，使用getChild方法
                List<ProductCategory> childList = getChild(nv.getId(),allProductCategory);
                //给根节点设置子节点
                nv.setChildren(childList);
            }

            return rootProductCategory;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //分页查询分类列表
    @Override
    public PaginationVo<ProductCategory> findProductCategoryList(Integer pageNum, Integer pageSize, ProductCategory productCategory) {
        PaginationVo<ProductCategory> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<ProductCategory> consumerList = productCategoryMapper.selectCategoryList(productCategory);
        //获取总记录数pageInfo.getTotal()
        PageInfo<ProductCategory> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;
    }

    //删除分类及其下所有子分类
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        int res = 0;
        //查找该id结点下所有子节点
        List<ProductCategory> productCategoryList = productCategoryMapper.getListById(id);
        //执行删除
        for (int i = 0; i < productCategoryList.size(); i++) {
            res += productCategoryMapper.deleteById(productCategoryList.get(i).getId());
        }
        if (res == productCategoryList.size()){
            return true;
        }else {
            return false;
        }
    }

    private List<ProductCategory> getChild(Long id, List<ProductCategory> allProductCategory) {
        //子菜单
        List<ProductCategory> childList = new ArrayList<>();
        for (ProductCategory nav : allProductCategory) {
            //遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点
            if(nav.getPid().longValue() == id.longValue()){
                childList.add(nav);
            }
        }
        //递归设置子节点
        for (ProductCategory nav : childList) {
            nav.setChildren(getChild(nav.getId(),allProductCategory));
        }

        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<ProductCategory>();
        }
        return childList;
    }
}
