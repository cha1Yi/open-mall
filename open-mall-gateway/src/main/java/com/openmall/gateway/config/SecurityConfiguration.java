package com.openmall.gateway.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

/**
 * 客户端配置
 *
 * @author wuxuan
 * @since 2024/7/4 14:46:58
 */
@Setter
@ConfigurationProperties(prefix = "security")
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@RefreshScope
public class SecurityConfiguration {

    /**
     * 黑名单请求列表
     */
    private List<String> blackList;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(
                        exchange -> {
                            if (CollectionUtil.isNotEmpty(blackList)) {
                                exchange.pathMatchers(blackList.toArray(new String[]{})).authenticated();
                            }
                            exchange.anyExchange().permitAll();
                        }
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

}
