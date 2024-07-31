package com.openmall.passport.application.oauth2.extend.password;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import com.openmall.passport.application.OAuth2AuthenticationProviderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuxuan
 * @since 2024/7/5 14:28:24
 */
@Slf4j
public class PasswordAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);
    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;


    /**
     * Constructs an {@code OAuth2ResourceOwnerPasswordAuthenticationProviderNew} using the provided parameters.
     *
     * @param authenticationManager the authentication manager
     * @param authorizationService  the authorization service
     * @param tokenGenerator        the token generator
     * @since 0.2.3
     */
    public PasswordAuthenticationProvider(AuthenticationManager authenticationManager, OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @SuppressWarnings("VulnerableCodeUsages")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PasswordAuthenticationToken passwordAuthenticationToken = (PasswordAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(passwordAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        // 验证客户端是否支持授权类型(grant_type=password)
        if (registeredClient == null || !registeredClient.getAuthorizationGrantTypes()
                .contains(PasswordAuthenticationToken.PASSWORD)) {
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT), OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        // 生成用户名密码身份验证令牌
        Map<String, Object> additionalParameters = passwordAuthenticationToken.getAdditionalParameters();
        String username = Convert.toStr(additionalParameters.get(OAuth2ParameterNames.USERNAME));
        String password = Convert.toStr(additionalParameters.get(OAuth2ParameterNames.PASSWORD));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 用户名密码身份验证。成功后返回带有权限的认证信息
        Authentication usernamePasswordAuthentication;
        try {
            usernamePasswordAuthentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            // 需要将其他类型的异常转换为 OAuth2AuthenticationException 才能被自定义异常捕获处理，逻辑源码 OAuth2TokenEndpointFilter#doFilterInternal
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "用户认证异常", ERROR_URI), e);
        }


        // 验证申请访问范围（Scope）
        Set<String> authorizedScopes = registeredClient.getScopes();
        Set<String> requestedScopes = Optional.ofNullable(passwordAuthenticationToken.getScopes()).orElse(Set.of());

        if (CollectionUtil.isNotEmpty(requestedScopes)) {
            Set<String> unauthorizedScopes = requestedScopes.stream()
                    .filter(requestedScope -> !registeredClient.getScopes().contains(requestedScope))
                    .collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(unauthorizedScopes)) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
            }
            authorizedScopes = new LinkedHashSet<>(requestedScopes);
        }

        // 访问令牌构造器
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(PasswordAuthenticationToken.PASSWORD)
                .authorizationGrant(passwordAuthenticationToken);


        // 生成访问令牌(Access Token)
        DefaultOAuth2TokenContext auth2AccessTokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .build();
        OAuth2Token oAuth2AccessToken = this.tokenGenerator.generate(auth2AccessTokenContext);

        if (oAuth2AccessToken == null) {
            OAuth2Error oAuth2Error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(oAuth2Error);
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, oAuth2AccessToken.getTokenValue(), oAuth2AccessToken.getIssuedAt(), oAuth2AccessToken.getExpiresAt(), auth2AccessTokenContext.getAuthorizedScopes());
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(usernamePasswordAuthentication.getName())
                .authorizationGrantType(PasswordAuthenticationToken.PASSWORD)
                .authorizedScopes(authorizedScopes)
                .attribute(Principal.class.getName(), usernamePasswordAuthentication);

        if (oAuth2AccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) oAuth2AccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        //生成刷新令牌（refresh token）
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            DefaultOAuth2TokenContext auth2RefreshTokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN)
                    .build();
            OAuth2Token oAuth2RefreshToken = this.tokenGenerator.generate(auth2RefreshTokenContext);
            if (!(oAuth2RefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error oAuth2Error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(oAuth2Error);
            }
            refreshToken = (OAuth2RefreshToken) oAuth2RefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }
        // ---- ID TOKEN -----
        OidcIdToken idToken;
        if (requestedScopes.contains(OidcScopes.OPENID)) {
            DefaultOAuth2TokenContext auth2IdTokenContext = tokenContextBuilder.tokenType(ID_TOKEN_TOKEN_TYPE)
                    .authorization(authorizationBuilder.build())
                    .build();
            OAuth2Token generatedIdToken = this.tokenGenerator.generate(auth2IdTokenContext);
            if (!(generatedIdToken instanceof Jwt)) {
                OAuth2Error oAuth2Error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, "The token generator failed to generate the id token.", ERROR_URI);
                throw new OAuth2AuthenticationException(oAuth2Error);
            }

            log.trace("Generated ID Token");

            idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(), generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
            authorizationBuilder.token(idToken, (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
        } else {
            idToken = null;
        }

        OAuth2Authorization authorization = authorizationBuilder.build();
        this.authorizationService.save(authorization);

        additionalParameters = (idToken != null) ? Collections.singletonMap(OidcParameterNames.ID_TOKEN, idToken.getTokenValue()) : Collections.emptyMap();

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    /**
     * 判断传入的 authentication 类型是否与当前认证提供者(AuthenticationProvider)相匹配--模板方法
     * <p>
     * ProviderManager#authenticate 遍历 providers 找到支持对应认证请求的 provider-迭代器模式
     *
     * @param authentication 认证请求
     * @return true/false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
