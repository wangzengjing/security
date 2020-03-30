package com.wzj.security.mapper;


import com.wzj.security.domain.TbPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbPermissionMapper extends MyMapper<TbPermission> {

    List<TbPermission> selectByUserId(@Param("userId") Long userId);

    List<String> selectRoleByPid(@Param("pId") Long pId);
}