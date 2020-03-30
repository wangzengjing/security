package com.wzj.security.domain;

import javax.persistence.*;
import lombok.Data;

/**
    * 角色权限表
    */
@Data
@Table(name = "tb_role_permission")
public class TbRolePermission {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 角色 ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限 ID
     */
    @Column(name = "permission_id")
    private Long permissionId;
}