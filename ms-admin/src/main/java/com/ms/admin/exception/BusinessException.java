package com.ms.admin.exception;

/**
 * 业务异常，可处理的异常，返回给客户
 * Created by mark.zhu on 2016/1/28.
 */
public class BusinessException extends Exception {
    /**
     * 非法请求，如参数校验不通过
     */
    protected static int ERROR_CODE_BAD_REQUEST = 1001;

    /**
     * 业务校验失败，不符合业务规则: 如重复的昵称(数据库唯一索引抛出异常)，字段超出数据库定义长度等
     */
    protected static int ERROR_CODE_BUSI_CHECK_FAILED = 1002;

    /**
     * 请求资源未授权，如查看别人的订单等
     */
    protected static int ERROR_CODE_UNAUTHORIZED = 1003;

    /**
     * 数据库服务抛出的异常，如SQL语法错误、执行SQL语句失败
     */
    protected static int ERROR_CODE_DAO_SQL = 1100;


    protected int errorCode;

    public BusinessException(int errorCode) {
        this.errorCode = errorCode;
    }
    public BusinessException(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode,Throwable cause) {
        this(errorCode,cause,cause.getMessage());
    }

    public BusinessException( int errorCode,Throwable cause,String message) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public static class BadRequestException extends BusinessException {
        public BadRequestException() {
            super(ERROR_CODE_BAD_REQUEST);
        }
    }

    public static class UnAuthorizedAccessException extends BusinessException {
        public UnAuthorizedAccessException() {
            super(ERROR_CODE_UNAUTHORIZED);
        }
    }
}
