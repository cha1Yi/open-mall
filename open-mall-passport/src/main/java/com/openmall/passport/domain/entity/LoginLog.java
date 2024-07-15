package com.openmall.passport.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登陆日志
 * @author wuxuan
 * @since 2024/7/15 09:58:37
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "passport_login_log", indexes = {@Index(name = "idx_user_id", columnList = "user_id")})
@Data
public class LoginLog extends BaseEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 登陆ip
     */
    private Long ip;

    /**
     * 地区
     */
    private String region;

    /**
     * 客户端信息
     */
    private String clientInfo;
}
