package com.ms.admin.controller;

import com.ms.admin.controller.user.CustomPrimitiveNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by mark.zhu on 2016/2/25.
 */
public class CustomWebBindingInitializer implements WebBindingInitializer {
    private static final String BLANK = " ";

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(BLANK, true));
        binder.registerCustomEditor(byte.class, new CustomPrimitiveNumberEditor(byte.class));
        binder.registerCustomEditor(short.class, new CustomPrimitiveNumberEditor(short.class));
        binder.registerCustomEditor(int.class, new CustomPrimitiveNumberEditor(int.class));
        binder.registerCustomEditor(long.class, new CustomPrimitiveNumberEditor(long.class));
        binder.registerCustomEditor(float.class, new CustomPrimitiveNumberEditor(float.class));
        binder.registerCustomEditor(double.class, new CustomPrimitiveNumberEditor(double.class));
    }
}
