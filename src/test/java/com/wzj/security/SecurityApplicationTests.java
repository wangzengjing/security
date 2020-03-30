package com.wzj.security;

import com.wzj.security.domain.TbPermission;
import com.wzj.security.service.TbPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

    @Autowired
    private TbPermissionService tbPermissionService;

    @Test
    void contextLoads() {
        TbPermission tbPermissionByUrl = tbPermissionService.getTbPermissionByUrl("/users/");

        System.out.println(tbPermissionByUrl.getId());
    }


}
