package com.wzj.security.service.impl;


import com.wzj.security.domain.TbRole;
import com.wzj.security.mapper.TbRoleMapper;
import com.wzj.security.service.TbRolePermissionService;
import com.wzj.security.service.TbRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbRoleServiceImpl implements TbRoleService {

    @Resource
    private TbRoleMapper tbRoleMapper;

    @Resource
    private TbRolePermissionService tbRolePermissionService;

    @Override
    public List<TbRole> selectByUserId(Long userId) {
        return tbRoleMapper.selectByUserId(userId);

    }




}
