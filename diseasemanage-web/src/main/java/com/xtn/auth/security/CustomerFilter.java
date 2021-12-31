package com.xtn.auth.security;

import com.aliyun.oss.common.utils.StringUtils;
import com.xtn.domain.Menu;
import com.xtn.domain.Role;
import com.xtn.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 权限控制
 * 根据url分析请求所需的角色
 */
@Component
@Slf4j
public class CustomerFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    private MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("CustomerFilter（32）---------------->" + requestUrl);
//        List<Menu> menuByRole = menuService.getMenuByRole();
//        for (Menu m: menuByRole) {
//            //判断请求url与菜单角色是否匹配
//            if (Objects.isNull(m.getUrl())) {
//                continue;
//            }
//            if (antPathMatcher.match(m.getUrl(),requestUrl)){
//                String[] str = m.getRoles().stream().map(Role::getRoleName).toArray(String[]::new);
//                log.info("菜单所需角色：{}", Arrays.toString(str));
//                return SecurityConfig.createList(str);
//            }
//        }
        if (requestUrl.contains("?")) {
            requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
        }
        //根据url查询菜单所需角色列表
        List<String> authList = menuService.getRoleListByUrl(requestUrl);
        if (Objects.nonNull(authList) && authList.size() > 0) {
            String[] strings = new String[authList.size()];
            String[] str = authList.toArray(strings);
            log.info("菜单所需角色：{}", Arrays.toString(str));
            return SecurityConfig.createList(str);
        }
        System.out.println("CustomerFilter（41）---------------->" + requestUrl);
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
