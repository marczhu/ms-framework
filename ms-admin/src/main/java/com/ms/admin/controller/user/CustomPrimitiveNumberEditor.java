package com.ms.admin.controller.user;

import org.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

/**
 * see {@link org.springframework.beans.propertyeditors.CustomNumberEditor}
 * 支持基本类型，基本类型默认值设置为0
 * 否则设置为null
 * Created by mark.zhu on 2016/2/25.
 */
public class CustomPrimitiveNumberEditor extends PropertyEditorSupport {
    private final Class<?> numberClass;


    private final boolean allowEmpty;

    public CustomPrimitiveNumberEditor(Class<?> numberClass) throws IllegalArgumentException {
        this(numberClass, true);
    }

    public CustomPrimitiveNumberEditor(Class<?> numberClass,boolean allowEmpty) throws IllegalArgumentException {
        if (numberClass == null ) {
            throw new IllegalArgumentException("Property class must be specified!");
        }
        this.numberClass = numberClass;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        if (value == null) {
            if (numberClass.isPrimitive()){
                return "0";
            }else{
                return null;
            }
        } else {
            // Use toString method for rendering value.
            return value.toString();
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            if (numberClass.isPrimitive()) {
                setValue(getNumberValue("0"));
            }else{
                setValue(null);
            }
        }else {
            setValue(getNumberValue(text));
        }
    }

    private Object getNumberValue(String text){
        if (numberClass.equals(byte.class) || numberClass.equals(Byte.class)) {
            return Byte.parseByte(text);
        }
        else if (numberClass.equals(short.class) || numberClass.equals(Short.class)) {
            return Short.parseShort(text);
        }
        else if (numberClass.equals(int.class) || numberClass.equals(Integer.class)) {
            return Integer.parseInt(text);
        }
        else if (numberClass.equals(long.class) || numberClass.equals(Long.class)) {
            return Long.parseLong(text);
        }
        else if (numberClass.equals(float.class) || numberClass.equals(Float.class)) {
            return Float.parseFloat(text);
        }
        else if (numberClass.equals(double.class) || numberClass.equals(Double.class)) {
            return Double.parseDouble(text);
        }else {
            throw new IllegalArgumentException(
                    "Cannot convert String [" + text + "] to target class [" + numberClass.getName() + "]");        }
    }
}
