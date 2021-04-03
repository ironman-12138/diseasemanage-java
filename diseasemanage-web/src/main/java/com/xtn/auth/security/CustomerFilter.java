package com.xtn.auth.security;

import com.xtn.domain.Menu;
import com.xtn.domain.Role;
import com.xtn.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据url分析请求所需的角色
 */
@Component
public class CustomerFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    private MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menuByRole = menuService.getMenuByRole();
        for (Menu m: menuByRole) {
            //判断请求url与菜单角色是否匹配
            if (antPathMatcher.match(m.getUrl(),requestUrl)){
                String[] str = m.getRoles().stream().map(Role::getRoleName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        System.out.println("CustomerFilter（40）---------------->" + requestUrl);
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
