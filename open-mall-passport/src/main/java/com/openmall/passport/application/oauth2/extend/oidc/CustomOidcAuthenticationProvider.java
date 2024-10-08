package com.openmall.passport.application.oauth2.extend.oidc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;

/**
 * 自定义 OIDC 认证提供者
 *
 * @author wuxuan
 * @since 2024/7/5 17:24:23
 */
@Slf4j
public class CustomOidcAuthenticationProvider implements AuthenticationProvider {


    private final OAuth2AuthorizationService authorizationService;

    public CustomOidcAuthenticationProvider(OAuth2AuthorizationService authorizationService) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.authorizationService = authorizationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OidcUserInfoAuthenticationToken userInfoAuthentication = (OidcUserInfoAuthenticationToken) authentication;
        AbstractOAuth2TokenAuthenticationToken<?> accessTokenAuthentication = null;
        if (AbstractOAuth2TokenAuthenticationToken.class.isAssignableFrom(userInfoAuthentication.getPrincipal()
                .getClass())) {
            accessTokenAuthentication = (AbstractOAuth2TokenAuthenticationToken) userInfoAuthentication.getPrincipal();
        }

        if (accessTokenAuthentication != null && accessTokenAuthentication.isAuthenticated()) {
            String accessTokenValue = accessTokenAuthentication.getToken().getTokenValue();
            OAuth2Authorization authorization = this.authorizationService.findByToken(accessTokenValue, OAuth2TokenType.ACCESS_TOKEN);
            if (authorization == null) {
                throw new OAuth2AuthenticationException("invalid_token");
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Retrieved authorization with access token");
                }

                OAuth2Authorization.Token<OAuth2AccessToken> authorizedAccessToken = authorization.getAccessToken();
                if (!authorizedAccessToken.isActive()) {
                    throw new OAuth2AuthenticationException("invalid_token");
                } else {
                    // 从认证结果中获取userInfo
                    CustomOidcUserInfo customOidcUserInfo = (CustomOidcUserInfo) userInfoAuthentication.getUserInfo();
                    // 从authorizedAccessToken中获取授权范围
                    //noinspection unchecked
                    Set<String> scopeSet = Optional.ofNullable(authorizedAccessToken.getClaims())
                            .map(item -> new HashSet<>((Collection<String>) item.get("scope")))
                            .orElse(new HashSet<>());
                    // 获取授权范围对应userInfo的字段信息
                    Map<String, Object> claims = DefaultOidcUserInfoMapper.getClaimsRequestedByScope(customOidcUserInfo.getClaims(), scopeSet);
                    return new CustomOidcToken(accessTokenAuthentication, new CustomOidcUserInfo(claims));
                }
            }
        } else {
            throw new OAuth2AuthenticationException("invalid_token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OidcUserInfoAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private static final class DefaultOidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, CustomOidcUserInfo> {
        private static final String ADDRESS_SCOPE = "address";
        private static final String EMAIL_SCOPE = "email";
        private static final String PHONE_SCOPE = "phone";
        private static final String PROFILE_SCOPE = "profile";

        private static final List<String> EMAIL_CLAIMS = List.of("email");
        private static final List<String> ADDRESS_CLAIMS = List.of("address");
        private static final List<String> PHONE_CLAIMS = List.of("mobile");
        private static final List<String> PROFILE_CLAIMS = List.of("username", "realName", "nickname", "avatar");

        private DefaultOidcUserInfoMapper() {
        }

        @Override
        public CustomOidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
            OAuth2Authorization authorization = authenticationContext.getAuthorization();
            OidcIdToken idToken = Objects.requireNonNull(authorization.getToken(OidcIdToken.class)).getToken();
            OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
            Map<String, Object> scopeRequestedClaims = getClaimsRequestedByScope(idToken.getClaims(), accessToken.getScopes());
            return new CustomOidcUserInfo(scopeRequestedClaims);
        }

        /**
         * 根据不同权限抽取不同数据
         * @param requestedScopes 申请的范围：openid profile email phone
         * @param claims 认证结果
         */
        private static Map<String, Object> getClaimsRequestedByScope(Map<String, Object> claims, Set<String> requestedScopes) {
            Set<String> scopeRequestedClaimNames = new HashSet<>(32);
            scopeRequestedClaimNames.add("sub");


            if (requestedScopes.contains(ADDRESS_SCOPE)) {
                scopeRequestedClaimNames.addAll(ADDRESS_CLAIMS);
            }

            if (requestedScopes.contains(EMAIL_SCOPE)) {
                scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
            }

            if (requestedScopes.contains(PHONE_SCOPE)) {
                scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
            }

            if (requestedScopes.contains(PROFILE_SCOPE)) {
                scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
            }

            Map<String, Object> requestedClaims = new HashMap<>(claims);
            requestedClaims.keySet().removeIf((claimName) -> !scopeRequestedClaimNames.contains(claimName));
            return requestedClaims;
        }
    }

}
