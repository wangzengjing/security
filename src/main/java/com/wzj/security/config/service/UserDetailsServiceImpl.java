package com.wzj.security.config.service;

import com.wzj.security.domain.TbPermission;
import com.wzj.security.domain.TbRole;
import com.wzj.security.domain.TbUser;
import com.wzj.security.service.TbPermissionService;
import com.wzj.security.service.TbRoleService;
import com.wzj.security.service.TbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private TbPermissionService tbPermissionService;

    @Autowired
    private TbRoleService tbRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TbUser tbUser = tbUserService.getByUsername(username);

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList();

        if(tbUser != null){
//            List<TbPermission> tbPermissions = tbPermissionService.selectByUserId(tbUser.getId());
//            tbPermissions.forEach(tbPermission -> {
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
//                log.info("登录账号{},账号所具有的权限{}",username,tbPermission.getEnname());
//                grantedAuthorities.add(grantedAuthority);
//            });

            List<TbRole> tbRoles = tbRoleService.selectByUserId(tbUser.getId());
            tbRoles.forEach(tbRole->{
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbRole.getEnname());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new User(tbUser.getUsername(),tbUser.getPassword(),grantedAuthorities);
    }
}
