package com.openmall.system.domain.entity;

import com.openmall.jpa.converters.ListLongToStringJoinOnCommaConverter;
import com.openmall.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wuxuan
 * @since 2024/7/23 15:17:17
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_role", indexes = {@Index(name = "idx_parent", columnList = "parent")})
@Data
public class Role extends BaseEntity {
    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 父级节点
     */
    private Long parentId;

    /**
     * 节点所在的路径，如，0,1,2
     */
    @Convert(converter = ListLongToStringJoinOnCommaConverter.class)
    private List<Long> routes;

}
