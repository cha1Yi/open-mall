package com.openmall.system.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuxuan
 * @since 2024/7/23 17:17:27
 */
@AllArgsConstructor
@Getter
public enum AuthorityTypeEnum {

    front("前端"),
    backend("后端");

    private final String desc;
}
