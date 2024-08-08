package com.openmall.dubbo.api.system.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户详情
 * @author wuxuan
 * @since 2024/7/23 14:51:05
 */
@Data
public class SystemUserDetailVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 610L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     *  用户名
     */
    private String username;

    private String nickname;

    private String mobile;

    private String email;

    /**
     *密码
     */
    private String password;

    /**
     *锁定状态
     */
    private boolean locked;

    /**
     *启用状态
     */
    private boolean enabled;

    /**
     * 用户密码失效时间时间
     */
    private LocalDateTime passwordExpireTime;
    /**
     *授权的权限
     */
    private List<String> grantedAuthorities;


}
