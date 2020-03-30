package com.wzj.security.filter;

import com.wzj.security.domain.TbPermission;
import com.wzj.security.service.TbPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @Description: FilterInvocationSecurityMetadataSource（权限资源过滤器接口）继承了 SecurityMetadataSource（权限资源接口）
 * Spring Security是通过SecurityMetadataSource来加载访问时所需要的具体权限；Metadata是元数据的意思。
 * 自定义权限资源过滤器，实现动态的权限验证
 * 它的主要责任就是当访问一个url时，返回这个url所需要的访问权限
 **/

@Component
@Slf4j
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private TbPermissionService tbPermissionService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();



    /**
     * @Description: 返回本次访问需要的权限，可以有多个权限
     * @Param: [o]
     * @return: java.util.Collection<org.springframework.security.access.ConfigAttribute>
     **/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //数据库查询suo所有的权限

        List<TbPermission> allPermission = tbPermissionService.getAllPermission();

        for (TbPermission tbPermission: allPermission) {
            //使用路径匹配工具进行路径匹配

            if (antPathMatcher.match(tbPermission.getUrl(), requestUrl)) {

                List<String> tbRoles = tbPermissionService.getTbRoleByPid(tbPermission.getId());

                if (tbRoles.size() > 0) {
                    log.info("当前访问路径是{},这个url所需要的访问权限是{}", requestUrl, tbRoles);

                    //将List 转换为 String数组
                    String[] values = (String[]) tbRoles.toArray(new String[tbRoles.size()]);

                    return SecurityConfig.createList(values);
                }


            }
        }

        log.info("当前访问路径是{},这个url所需要的访问权限是{}", requestUrl, "ROLE_LOGIN");
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    /**
     * @Description: 此处方法如果做了实现，返回了定义的权限资源列表，
     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，
     * 如果不需要校验，这里实现方法，方法体直接返回null即可。
     * @Param: []
     * @return: java.util.Collection<org.springframework.security.access.ConfigAttribute>
     **/
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }




    /**
     * @Description: 方法返回类对象是否支持校验，
     * web项目一般使用FilterInvocation来判断，或者直接返回true
     * @Param: [Class]
     * @return: boolean
     **/
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
