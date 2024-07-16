package com.openmall.system.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户基础表
 * @author wuxuan
 * @since 2024/7/15 17:23:21
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_user",indexes = {@Index(name = "idx_username", columnList = "username")})
@Data
public class SystemUser extends BaseEntity {
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 锁定状态
     */
    private boolean locked;

    /**
     * 启用状态
     */
    private boolean enabled;

}
