package com.itheima.exception;

/**
 * @author liam
 * @description
 * @date 2020/2/26-18:50
 * @Version 1.0.0
 */
public class CheckGroupException extends RuntimeException {
    public CheckGroupException() {
        super();
    }

    public CheckGroupException(String message) {
        super(message);
    }

    public CheckGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckGroupException(Throwable cause) {
        super(cause);
    }

    protected CheckGroupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
