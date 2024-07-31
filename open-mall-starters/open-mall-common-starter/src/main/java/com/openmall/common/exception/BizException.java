package com.openmall.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wuxuan
 * @since 2024/7/30 11:22:26
 */
@Getter
@Setter
public class BizException extends RuntimeException {
    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

}
