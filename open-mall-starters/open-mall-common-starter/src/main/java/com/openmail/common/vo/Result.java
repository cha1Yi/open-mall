package com.openmail.common.vo;


import com.openmail.common.exception.ErrorCode;

import java.time.LocalDateTime;

/**
 * 响应结果
 *
 * @param data      响应数据
 * @param errorCode 错误码
 * @param msg       错误信息
 * @param success   是否是成功的响应
 * @param timestamp 时间戳
 * @param <T>       数据类型
 * @author wuxuan
 * @since 2024/7/4 14:23:51
 */
public record Result<T>(T data, boolean success, int errorCode, String msg, LocalDateTime timestamp) {

    /**
     * 正常响应
     *
     * @param data 响应的数据体
     * @param <T>  数据类型
     * @return 返回的对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data, true, 0, "成功", LocalDateTime.now());
    }

    /**
     * 失败的响应
     *
     * @param errorCode 业务错误码
     * @param msg       消息
     * @return 返回的对象
     */
    public static Result<Object> fail(int errorCode, String msg) {
        return new Result<>(null, false, errorCode, msg, LocalDateTime.now());
    }

    /**
     * 失败的响应
     *
     * @param errorCode 业务错误码
     * @return 返回的对象
     */
    public static Result<Object> fail(ErrorCode errorCode) {
        return Result.fail(errorCode.getCode().value(), errorCode.getMessage());
    }
}
