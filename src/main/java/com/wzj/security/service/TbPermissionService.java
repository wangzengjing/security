package com.wzj.security.service;



import com.wzj.security.domain.TbPermission;

import java.util.List;

public interface TbPermissionService{

    /**
     * 根据ID查询权限
     * @param userId
     * @return
     */
    List<TbPermission> selectByUserId(Long userId);

    /**
     * 查询所有权限
     * @return
     */
    List<TbPermission> getAllPermission();


}
