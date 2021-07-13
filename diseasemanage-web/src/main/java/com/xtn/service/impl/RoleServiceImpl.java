package com.xtn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtn.domain.Consumer;
import com.xtn.domain.Menu;
import com.xtn.domain.Role;
import com.xtn.mapper.RoleMapper;
import com.xtn.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtn.vo.PaginationVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xcoder
 * @since 2021-02-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    //分页模糊查询角色列表
    @Override
    public PaginationVo<Role> findRoleList(Integer pageNum, Integer pageSize, Role role) {
        PaginationVo<Role> vo = new PaginationVo<>();
        //pageNum:查询的页数，pageSize:一页显示的数量
        PageHelper.startPage(pageNum,pageSize);
        List<Role> consumerList = roleMapper.selectRoleList(role);
        //获取总记录数pageInfo.getTotal()
        PageInfo<Role> pageInfo = new PageInfo<>(consumerList);
        vo.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        vo.setDataList(consumerList);
        return vo;

    }

    //获取角色权限id列表
    @Override
    public List<Long> findMenuIdsByRoleId(Long id) {
        List<Menu> list = roleMapper.findMenuIdsByRoleId(id);
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ids.add(list.get(i).getId());
        }
        return ids;
    }

    @Override
    @Transactional
    public boolean authority(Long id,long... longs) {
        //清除角色原本的权限
        roleMapper.remove(id);
        for (int i = 0; i < longs.length; i++) {
            roleMapper.authority(id,longs[i]);
        }
        return true;
    }
}
