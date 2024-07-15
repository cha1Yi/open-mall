package com.openmall.members.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  会员用户详情
 *
 * @author wuxuan
 * @since 2024/7/15 17:30:05
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "system_member_user_details")
public class MemberUserDetails extends BaseEntity {
    /**
     * 用户id
     */
    @Id
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;
}
