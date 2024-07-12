package com.openmall.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo相关的配置
 * @author wuxuan
 * @since 2024/7/12 11:29:52
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.openmall")
public class DubboConfiguration {
}
