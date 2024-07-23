package com.openmall.dubbo.api.system.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户详情
 * @author wuxuan
 * @since 2024/7/23 14:51:05
 */
public record SystemUserDetailVO(
        /*
          用户ID
         */

        Long id,

        /*
          用户名
         */
        String username,

        /*
          密码
         */
        String password,

        /*
          锁定状态
         */
        boolean locked,

        /*
          启用状态
         */
        boolean enabled,

        /*
          用户密码过期时间
         */
        LocalDateTime passwordExpireTime,
        /*
          授权的权限
         */
        List<String> grantedAuthorities) {
}
