package com.openmail.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 安全工具类
 *
 * @author wuxuan
 * @since 2024/7/4 16:02:52
 */
public class SecurityUtils {
    /**
     * 获取用户身份认证的认证令牌
     *
     * @return Authentication
     */
    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * 获取用户会话属性
     *
     * @return 用户会话属性
     */
    public static Map<String, Object> getUserSessionProperties() {
        Optional<Authentication> authenticationOptional = getAuthentication();
        if (authenticationOptional.isPresent() && authenticationOptional.get() instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes();
        }
        return Map.of();
    }


    /**
     * 是否是超级管理员
     *
     * @return true/false
     */
    public static boolean isSuperAdmin() {
        return false;
    }

    /**
     * 获取用户角色
     *
     * @return 角色集合
     */
    public static Set<String> getRoles() {
        return Set.of();
    }
}
