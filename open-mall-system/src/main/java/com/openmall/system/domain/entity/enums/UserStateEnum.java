package com.openmall.system.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 * @author wuxuan
 * @since 2024/7/23 15:05:48
 */
@Getter
@AllArgsConstructor
public enum UserStateEnum {
    NORMAL("正常"),
    FREEZE("冻结"),
    DISABLED("锁定");

    private final String desc;
}
