package com.openmall.passport;

import com.openmall.dubbo.api.system.SayHelloApi;
import jakarta.annotation.PostConstruct;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * passport 服务启动类
 *
 * @author wuxuan
 * @since 2024/7/3 21:54:28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PassportApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassportApplication.class, args);
    }


    @DubboReference
    private SayHelloApi sayHelloApi;
    @PostConstruct
    public void init(){
        this.sayHelloApi.sayHello();
    }
}
