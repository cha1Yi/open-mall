package com.openmall.members;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wuxuan
 * @since 2024/7/15 17:45:30
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MembersApplication {
    public static void main(String[] args) {
        SpringApplication.run(MembersApplication.class, args);
    }
}
