package com.openmall.passport.config;

import com.openmall.passport.model.MemberUserDetails;
import com.openmall.passport.model.SystemUserDetails;
import com.openmall.common.constant.JwtClaimConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuxuan
 * @since 2024/7/5 10:28:35
 */
@Configuration
public class JwtTokenCustomizerConfiguration {

    /**
     * jwt token自定义
     *
     * @return OAuth2TokenCustomizer
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
                Optional.ofNullable(context.getPrincipal()).ifPresent(principal -> {
                    JwtClaimsSet.Builder claims = context.getClaims();
                    if (principal instanceof SystemUserDetails systemUserDetails) {
                        claims.claim(JwtClaimConstants.USER_ID, systemUserDetails.getUserId());

                        //将角色存入jwt中，解析JWT的角色用于鉴权的位置: com.openmall.common.security.config.ResourceServerConfiguration.jwtAuthenticationConverter
                        Set<String> authorities = AuthorityUtils.authorityListToSet(context.getPrincipal()
                                        .getAuthorities())
                                .stream()
                                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                        claims.claim(JwtClaimConstants.AUTHORITIES, authorities);
                    } else if (principal instanceof MemberUserDetails memberUserDetails) {
                        claims.claim(JwtClaimConstants.MEMBER_ID, memberUserDetails.getId());
                    }
                });
            }

        };
    }
}
