package com.wzj.security.service.impl;


import com.wzj.security.domain.TbUser;
import com.wzj.security.mapper.TbUserMapper;
import com.wzj.security.service.TbUserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;


    @Override
    public TbUser getByUsername(String username) {
        Example example = new Example(TbUser.class);

        example.createCriteria().andEqualTo("username",username);
        return tbUserMapper.selectOneByExample(example);
    }
}

