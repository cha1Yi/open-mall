package com.openmall.system.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户的详细信息
 * @author wuxuan
 * @since 2024/7/15 17:29:52
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "system_system_user_details")
public class SystemUserDetails extends BaseEntity {

    /**
     * 用户ID
     * @see SystemUser#getId()
     */
    @Id
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    private String mobile;

    /**
     * 头像
     */
    private String avatar;


}
