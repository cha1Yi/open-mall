package com.openmall.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuxuan
 * @since 2024/7/15 10:08:30
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {
    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", insertable = false)
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate(){
        this.createTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updateTime = LocalDateTime.now();
    }
}
