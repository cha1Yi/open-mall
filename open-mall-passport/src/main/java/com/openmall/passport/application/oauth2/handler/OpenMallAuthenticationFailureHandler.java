package com.openmall.passport.application.oauth2.handler;

import cn.hutool.core.util.StrUtil;
import com.openmall.common.exception.ErrorCode;
import com.openmall.common.json.JsonUtils;
import com.openmall.common.vo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author wuxuan
 * @since 2024/7/5 16:28:27
 */
@Slf4j
public class OpenMallAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * MappingJackson2HttpMessageConverter 是 Spring 框架提供的一个 HTTP 消息转换器，用于将 HTTP 请求和响应的 JSON 数据与 Java 对象之间进行转换
     */
    private final HttpMessageConverter<Object> accessTokenHttpResponseConverter = new MappingJackson2HttpMessageConverter(JsonUtils.ObjectMapperFactory.createObjectMapper());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            OAuth2Error error = oAuth2AuthenticationException.getError();
            log.error(StrUtil.format("登陆失败:{}", error.getDescription()), exception);
            try (ServletServerHttpResponse servletServerHttpResponse = new ServletServerHttpResponse(response)) {
                accessTokenHttpResponseConverter.write(Result.fail(ErrorCode.TOKEN_INVALID.getCode()
                        .value(), error.getErrorCode()), MediaType.APPLICATION_JSON, servletServerHttpResponse);
            }
        }
    }
}
