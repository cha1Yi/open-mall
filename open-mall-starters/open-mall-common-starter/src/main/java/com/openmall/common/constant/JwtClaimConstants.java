package com.openmall.common.constant;

/**
 * JWT（JSON Web Token）声明常量类。
 * 该类用于定义JWT中使用的一组预定义声明的常量。
 * 这些声明是JWT的标准化部分，根据JWT规范（RFC 7519）定义。
 *
 * @author wuxuan
 * @since 2024/7/5 09:50:01
 */
public interface JwtClaimConstants {
    /**
     * 权限(角色Code)集合
     */
    String AUTHORITIES = "authorities";

    /**
     * 会员ID
     */
    String MEMBER_ID = "member_id";

    /**
     * 用户ID
     */
    String USER_ID = "user_id";
}
