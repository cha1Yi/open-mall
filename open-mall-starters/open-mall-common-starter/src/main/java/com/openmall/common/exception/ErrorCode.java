package com.openmall.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 错误码
 *
 * @author wuxuan
 * @since 2024/7/4 14:41:01
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS(HttpStatus.OK, "成功"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "系统错误"),
    PARAM_ERROR(HttpStatus.BAD_REQUEST, "参数错误"),
    TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "请求过于频繁"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "权限不足，禁止访问"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "token无效或已过期");

    private final HttpStatus code;
    private final String message;

}
