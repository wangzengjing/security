package com.wzj.security.service.impl;


import com.wzj.security.domain.TbPermission;
import com.wzj.security.domain.TbUser;
import com.wzj.security.mapper.TbPermissionMapper;
import com.wzj.security.service.TbPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class TbPermissionServiceImpl implements TbPermissionService {

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    @Transactional(readOnly=false)
    public List<TbPermission> selectByUserId(Long userId) {
        return tbPermissionMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(readOnly=false)
    public List<TbPermission> getAllPermission() {
        return tbPermissionMapper.selectAll();
    }

    @Override
    @Transactional(readOnly=false)
    public List<String> getTbRoleByPid(Long pid) {
        List<String> roles = tbPermissionMapper.selectRoleByPid(pid);
        return roles;
    }


}
