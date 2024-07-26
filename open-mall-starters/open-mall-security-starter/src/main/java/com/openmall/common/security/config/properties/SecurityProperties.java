package com.openmall.common.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 安全配置
 * @author wuxuan
 * @since 2024/7/26 17:20:58
 */
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {
    /**
     * 白名单
     */
    private List<String> whiteList;

    /**
     * 黑名单
     */
    private List<String> blackList;
}
