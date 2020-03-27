package com.wzj.security.mapper;


import com.wzj.security.domain.TbRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbRoleMapper extends MyMapper<TbRole> {

    List<TbRole> selectByUserId(@Param("userId") Long userId);
}