package com.ms.admin.controller;

import com.google.common.base.MoreObjects;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考{@link org.springframework.http.ResponseEntity},实现json友好
 * Created by mark.zhu on 2016/1/11.
 */
public class RestResponse<T> {
    private int status;
    private T entity;
    private List<ResponseError> errors = new ArrayList<>();

    public static ResponseBuilder builder() {
        return new DefaultResponseBuilder();
    }

    public void addError(final String errMsg) {
        errors.add(new ResponseError(errMsg));
    }

    public void addError(int errCode, String errMsg) {
        errors.add(new ResponseError(errCode, errMsg));
    }

    public interface ResponseBuilder<T> {
        public ResponseBuilder ok();

        public ResponseBuilder status(HttpStatus status);

        public ResponseBuilder entity(T entity);

        public ResponseBuilder error(String errMsg);

        public ResponseBuilder error(int errCode, String errMsg);

        public boolean hasError();

        public RestResponse build();
    }

    private static class DefaultResponseBuilder implements ResponseBuilder {
        private RestResponse response;

        public DefaultResponseBuilder() {
            response = new RestResponse();
        }

        public ResponseBuilder ok() {
            status(HttpStatus.OK);
            return this;
        }

        public ResponseBuilder status(HttpStatus status) {
            response.setStatus(status.value());
            return this;
        }

        @Override
        public ResponseBuilder entity(Object entity) {
            response.setEntity(entity);
            return this;
        }

        @Override
        public ResponseBuilder error(String errMsg) {
            response.addError(errMsg);
            return this;
        }

        @Override
        public ResponseBuilder error(int errCode, String errMsg) {
            response.addError(errCode, errMsg);
            return this;
        }

        @Override
        public boolean hasError() {
            return response.getErrors() != null && response.getErrors().size() > 0;
        }

        public RestResponse build() {
            return response;
        }
    }

    public static class ResponseError {
        private int code;
        private String message;
        private static final int DEFAULT_ERROR_CODE = 1001;

        public ResponseError(String errMsg) {
            this(DEFAULT_ERROR_CODE, errMsg);
        }

        public ResponseError(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return super.toString() + MoreObjects.toStringHelper(this)
                    .add("code", code)
                    .add("message", message)
                    .toString();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }


    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("entity", entity)
                .add("errors", errors)
                .toString();
    }
}