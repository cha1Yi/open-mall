package com.openmall.passport.application.oauth2.extend.password;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

/**
 * 密码授权模式身份验证令牌
 *
 * @author wuxuan
 * @since 2024/7/5 14:01:26
 */
@Getter
public class PasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");

    /**
     * 令牌申请访问范围
     */
    private final Set<String> scopes;


    protected PasswordAuthenticationToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = scopes;
    }

    /**
     * 获取用户凭证（密码）
     */
    @Override
    public Object getCredentials() {
        return this.getAdditionalParameters().get(OAuth2ParameterNames.PASSWORD);
    }
}
