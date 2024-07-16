package com.openmall.members.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  会员用户
 *
 * @author wuxuan
 * @since 2024/7/15 17:30:05
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "system_member_user",indexes = {@Index(name = "idx_username", columnList = "username")})
public class MemberUser extends BaseEntity {

    @Id
    @GeneratedValue()
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
