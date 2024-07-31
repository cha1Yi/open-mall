package com.openmall.common.apidoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

/**
 * open api 配置
 *
 * @author wuxuan
 * @since 2024/7/3 20:53:53
 */
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private final ApplicationContext applicationContext;

    /**
     * OAuth2 认证 endpoint
     */
    @Value("${spring.security.oauth2.authorization-server.token-uri:}")
    private String tokenUrl;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                        .name(HttpHeaders.AUTHORIZATION)
                        .flows(new OAuthFlows().password(new OAuthFlow().tokenUrl(tokenUrl).refreshUrl(tokenUrl)))
                        .in(SecurityScheme.In.HEADER)
                        .scheme("Bearer")
                        .bearerFormat("JWT")))
                //接口全局添加Authorization 参数
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .info(new Info().title(applicationContext.getApplicationName())
                        .description(applicationContext.getDisplayName()));
    }
}
