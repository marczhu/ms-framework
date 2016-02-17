package com.ms.admin.controller;

import org.springframework.validation.ObjectError;

/**
 * Created by mark.zhu on 2016/1/28.
 */
public class ObjectErrorHelper {
    public static ObjectError DEFAULT_UNKNOWN_OBJECT_ERROR = new ObjectError("error.unknown", "操作失败,请重试!");

    public static ObjectError createDefaultError(final String message) {
        return new ObjectError("error.default.name", message);
    }

    public static ObjectError createError(final String key, final String message) {
        return new ObjectError(key, message);
    }
}
