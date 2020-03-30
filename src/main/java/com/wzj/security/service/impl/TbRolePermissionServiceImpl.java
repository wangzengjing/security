package com.wzj.security.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wzj.security.mapper.TbRolePermissionMapper;
import com.wzj.security.service.TbRolePermissionService;
@Service
public class TbRolePermissionServiceImpl implements TbRolePermissionService{

    @Resource
    private TbRolePermissionMapper tbRolePermissionMapper;

}
