package com.wzj.security.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "tb_user_role")
public class TbUserRole implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户 ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色 ID
     */
    @Column(name = "role_id")
    private Long roleId;

    private static final long serialVersionUID = 1L;
}