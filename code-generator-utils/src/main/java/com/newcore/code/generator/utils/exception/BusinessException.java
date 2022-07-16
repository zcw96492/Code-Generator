package com.newcore.code.generator.utils.exception;

import org.springframework.util.Assert;

/**
 * 业务异常处理类
 * @author zhouchaowei
 */
public class BusinessException extends RuntimeException {

    private String errorCode;

    /**
     * 异常与异常信息处理方法
     * @param message 异常信息
     * @param cause 异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 直接抛出异常信息
     * @param cause 异常信息
     */
    public BusinessException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * 异常状态码处理方法
     * @param errorCode 异常状态码
     */
    public BusinessException(String errorCode) {
        Assert.notNull(errorCode, "errorCode can't be null!");
        this.errorCode = errorCode;
    }

    /**
     * 状态码、异常信息处理方法
     * @param errorCode 异常状态码
     * @param message 异常信息
     */
    public BusinessException(String errorCode, String message) {
        super(message);
        Assert.notNull(errorCode, "errorCode can't be null!");
        this.errorCode = errorCode;
    }

    /**
     * 获取错误码
     * @return java.lang.String
     */
    public String getErrorCode() {
        return this.errorCode;
    }
}
