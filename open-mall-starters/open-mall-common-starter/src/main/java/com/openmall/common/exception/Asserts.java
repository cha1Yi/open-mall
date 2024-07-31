package com.openmall.common.exception;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 异常断言
 * @author wuxuan
 * @since 2024/7/30 11:23:05
 */
public class Asserts {
    /**
     * 断言为假则抛出异常
     * @param expect 期望结果
     * @param message 抛出异常
     */
    public static void assertTrue(boolean expect, String message, Object... args) {
        if (expect) {
            return;
        }
        throw new BizException(StrUtil.format(message, args));
    }

    /**
     * 断言为假则抛出异常
     * @param expect 期望结果
     * @param cause 上游异常
     * @param args 消息参数
     * @param message 抛出异常
     */
    public static void assertTrue(boolean expect, String message, Throwable cause, Object... args) {
        if (expect) {
            return;
        }

        throw new BizException(StrUtil.format(message, args), cause);
    }

    /**
     * 断言为假则抛出异常
     * @param expect 期望结果
     * @param cause 上游异常
     * @param args 消息参数
     * @param exceptionClass 指定抛出的异常类型
     * @param message 抛出异常
     */
    public static void assertTrue(boolean expect, String message, Class<? extends BizException> exceptionClass, Throwable cause, Object... args) {
        if (expect) {
            return;
        }
        try {
            Constructor<? extends BizException> constructor = exceptionClass.getConstructor(String.class, Throwable.class);
            throw constructor.newInstance(StrUtil.format(message, args), cause);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
