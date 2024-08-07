package com.openmall.common.security.extend;

import cn.hutool.json.JSONUtil;
import com.openmall.common.exception.ErrorCode;
import com.openmall.common.json.JsonUtils;
import com.openmall.common.vo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证失败处理
 *
 * @author wuxuan
 * @since 2024/7/5 10:09:12
 */
@Component
public class OpenMallAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JsonUtils.toJson(Result.fail(ErrorCode.TOKEN_INVALID)));
    }
}
