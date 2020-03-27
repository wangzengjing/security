package com.wzj.security.service;


import com.wzj.security.domain.TbUser;

public interface TbUserService {

    TbUser getByUsername(String username);
}

