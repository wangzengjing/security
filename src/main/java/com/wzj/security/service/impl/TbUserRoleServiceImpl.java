package com.wzj.security.service.impl;


import com.wzj.security.mapper.TbUserRoleMapper;
import com.wzj.security.service.TbUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TbUserRoleServiceImpl implements TbUserRoleService {

    @Resource
    private TbUserRoleMapper tbUserRoleMapper;

}
