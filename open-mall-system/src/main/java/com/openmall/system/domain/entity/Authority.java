package com.openmall.system.domain.entity;

import com.openmall.jpa.entity.BaseEntity;
import com.openmall.system.domain.entity.enums.AuthorityTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表
 * @author wuxuan
 * @since 2024/7/23 17:12:28
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_authority")
@Data
public class Authority extends BaseEntity {
    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限code
     */
    private String authorityCode;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限的类型
     */
    @Enumerated(EnumType.STRING)
    private AuthorityTypeEnum authorityType;

    /**
     * 服务名称
     */
    private String serviceName;

}
