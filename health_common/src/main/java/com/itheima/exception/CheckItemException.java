package com.itheima.exception;

/**
 * @author liam
 * @description
 * @date 2020/2/24-15:45
 * @Version 1.0.0
 */
public class CheckItemException extends RuntimeException {
    public CheckItemException() {
        super();
    }

    public CheckItemException(String message) {
        super(message);
    }

    public CheckItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckItemException(Throwable cause) {
        super(cause);
    }

    protected CheckItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
