package com.openmall.common.exception.system;

import com.openmall.common.exception.BizException;

/**
 * @author wuxuan
 * @since 2024/7/30 11:21:50
 */
public class SystemUserNotExistException extends BizException {
    public SystemUserNotExistException() {
    }

    public SystemUserNotExistException(String message) {
        super(message);
    }

    public SystemUserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
