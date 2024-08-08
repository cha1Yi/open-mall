package com.openmall.common.constant;

/**
 * JWT（JSON Web Token）声明常量类。
 * 该类用于定义JWT中使用的一组预定义声明的常量。
 * 这些声明是JWT的标准化部分，根据JWT规范（RFC 7519）定义。
 *
 *<p>
 * <a href="https://openid.net/specs/openid-connect-core-1_0.html#UserInfoError">5.1.  Standard Claims</a>
 *
 * @author wuxuan
 * @since 2024/7/5 09:50:01
 */

public interface JwtClaimConstants {


    /**
     * 用户ID标识
     */
    String SUB = "sub";


    /**
     * 用户名
     */
    String NAME = "name";
    /**
     * 昵称
     */
    String NICKNAME = "nickname";

    /**
     * 邮件
     */
    String EMAIL = "email";


    /**
     * 权限(角色Code)集合
     */
    String AUTHORITIES = "authorities";


}
