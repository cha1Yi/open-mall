//package com.openmall.common.security.config;
//
//import cn.hutool.core.collection.CollectionUtil;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
//import java.util.List;
//
///**
// * 资源服务器配置
// *
// * @author wuxuan
// * @since 2024/7/5 09:44:45
// */
//
//@EnableWebSecurity
//@ConfigurationProperties(prefix = "security")
//@Configuration(proxyBeanMethods = false)
//public class SecurityConfiguration {
//    /**
//     * 白名单路径列表
//     */
//    @Setter
//    public List<String> whiteList;
//
//
//    /**
//     * Spring Security 安全过滤器链配置
//     *
//     * @param http 安全配置
//     * @return 安全过滤器链
//     */
//    @Bean
//    @Order(0)
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
//        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
//        http.authorizeHttpRequests((requests) -> {
//            if (CollectionUtil.isNotEmpty(whiteList)) {
//                for (String whitelistPath : whiteList) {
//                    requests.requestMatchers(mvcMatcherBuilder.pattern(whitelistPath)).permitAll();
//                }
//            }
//            requests.anyRequest().authenticated();
//        }).csrf(AbstractHttpConfigurer::disable).formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//
//    /**
//     * 不走过滤器链的放行配置
//     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**"), AntPathRequestMatcher.antMatcher("/doc.html"), AntPathRequestMatcher.antMatcher("/swagger-resources/**"), AntPathRequestMatcher.antMatcher("/v3/api-docs/**"), AntPathRequestMatcher.antMatcher("/swagger-ui/**"));
//    }
//
//}
