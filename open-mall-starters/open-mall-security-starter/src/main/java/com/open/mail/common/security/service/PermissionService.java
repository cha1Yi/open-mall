package com.open.mail.common.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.open.mail.common.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.Set;

/**
 * @author wuxuan
 * @since 2024/7/4 17:50:53
 */
@Service("ss")
@RequiredArgsConstructor
public class PermissionService {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断用户是否拥有权限
     *
     * @param expectPermission 期望的权限
     * @return 是否拥有权限
     */
    public boolean hasPermission(String expectPermission) {
        if (StrUtil.isBlank(expectPermission)) {
            return false;
        }

        //超级管理员直接放行
        if (SecurityUtils.isSuperAdmin()) {
            return true;
        }

        //获取当前登录用户的所有角色
        Set<String> roles = SecurityUtils.getRoles();
        if (CollectionUtil.isEmpty(roles)) {
            return false;
        }

        //获取当前用户所有角色的权限列表
        Set<String> permissions = this.getRolePermissions(roles);
        if (CollectionUtil.isEmpty(permissions)) {
            return false;
        }
        // 判断当前登录用户的所有角色的权限列表中是否包含所需权限,匹配权限，支持通配符(* 等)
        return permissions.stream().anyMatch(permission -> PatternMatchUtils.simpleMatch(permission, expectPermission));
    }

    /**
     * 获取当前用户所有角色的权限列表
     * @param roles 角色
     * @return 权限列表
     */
    public Set<String> getRolePermissions(Set<String> roles) {
        return null;
    }
}
