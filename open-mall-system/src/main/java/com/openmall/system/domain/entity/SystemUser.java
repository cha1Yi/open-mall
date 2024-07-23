package com.openmall.system.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import com.openmall.system.domain.entity.enums.UserStateEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户基础表
 * @author wuxuan
 * @since 2024/7/15 17:23:21
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_user", indexes = {@Index(name = "idx_username", columnList = "username")})
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
     * 用户状态
     */
    @Enumerated(EnumType.STRING)
    private UserStateEnum userState;

    /**
     * 用户密码过期时间
     */
    private LocalDateTime passwordExpireTime;

}
