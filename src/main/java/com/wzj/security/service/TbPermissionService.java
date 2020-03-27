package com.wzj.security.service;



import com.wzj.security.domain.TbPermission;

import java.util.List;

public interface TbPermissionService{

    List<TbPermission> selectByUserId(Long userId);
}
