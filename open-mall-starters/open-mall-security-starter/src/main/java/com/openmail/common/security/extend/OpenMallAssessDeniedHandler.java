package com.openmail.common.security.extend;

import cn.hutool.json.JSONUtil;
import com.openmail.common.exception.ErrorCode;
import com.openmail.common.vo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 访问拒绝处理器
 * @author wuxuan
 * @since 2024/7/5 09:58:40
 */
@Component
public class OpenMallAssessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JSONUtil.toJsonStr(Result.fail(ErrorCode.FORBIDDEN)));
    }
}
