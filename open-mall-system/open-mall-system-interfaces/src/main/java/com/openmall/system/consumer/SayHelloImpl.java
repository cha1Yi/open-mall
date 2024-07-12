package com.openmall.system.consumer;

import com.openmall.system.api.SayHelloApi;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author wuxuan
 * @since 2024/7/12 14:13:15
 */
@DubboService
public class SayHelloImpl implements SayHelloApi {
    @Override
    public void sayHello() {
        System.out.println("sayHello");
    }
}
