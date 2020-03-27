package com.wzj.security.service;



import com.wzj.security.domain.TbRole;

import java.util.List;

public interface TbRoleService {

    List<TbRole> selectByUserId(Long userId);
}
