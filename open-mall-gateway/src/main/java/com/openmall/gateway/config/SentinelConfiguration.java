package com.openmall.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.openmail.common.exception.ErrorCode;
import com.openmail.common.vo.Result;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * sentinel配置,自定义网关流程异常
 *
 * @author wuxuan
 * @since 2024/7/4 14:23:51
 */
@Configuration
public class SentinelConfiguration {

    @PostConstruct
    public void init() {
        GatewayCallbackManager.setBlockHandler(
                (exchange, throwable) ->
                        ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromValue(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                                                .body(Result.fail(ErrorCode.TOO_MANY_REQUEST)))
                                )
        );
    }
}
