package com.ms.admin.exception;

/**
 * 系统异常:
 * 依赖服务异常，如数据库无法连接、service服务异常
 * 其他系统异常，如存储IO错误
 * Created by mark.zhu on 2016/1/29.
 */
public class SystemException extends Exception {
    /**
     * 传输层错误：
     * 如依赖的服务(包括数据库)无法连接、连接超时、SocketReadTimeOut
     */
    protected static int ERROR_CODE_TRANSPORT = 2000;
    /**
     * 未知错误
     */
    protected static int ERROR_CODE_UNKNOWN = 2999;

    protected int errorCode;

    public SystemException(int errorCode) {
        this.errorCode = errorCode;
    }

    public SystemException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(int errorCode, Throwable cause) {
        this(errorCode, cause, cause.getMessage());
    }

    public SystemException(int errorCode, Throwable cause, String message) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public static class UnknownException extends SystemException {
        public static String DEFAULT_MESSAGE = "系统内部错误，请稍后再试!";
        public static String LOG_MESSAGE_PREFIX = "未知错误！";
        public UnknownException() {
            super(ERROR_CODE_UNKNOWN, DEFAULT_MESSAGE);
        }

        public UnknownException(String message) {
            super(ERROR_CODE_UNKNOWN, message);
        }
    }
}
