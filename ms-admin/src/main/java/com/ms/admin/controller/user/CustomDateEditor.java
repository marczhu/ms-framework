package com.ms.admin.controller.user;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * see {@link org.springframework.beans.propertyeditors.CustomDateEditor}
 * SimpleDateFormat 非线程安全，因此每次重新创建一个
 * Created by mark.zhu on 2016/2/26.
 */
public class CustomDateEditor extends PropertyEditorSupport {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private final String pattern;

    private final boolean allowEmpty;

    private final int exactDateLength;

    public CustomDateEditor() {
        this(DEFAULT_PATTERN, true, DEFAULT_PATTERN.length());
    }
    public CustomDateEditor(String pattern) {
        this(pattern, true, DEFAULT_PATTERN.length());
    }

    public CustomDateEditor(String pattern, boolean allowEmpty, int exactDateLength) {
        this.pattern = pattern;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = exactDateLength;
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? new SimpleDateFormat(pattern).format(value) : "");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
            throw new IllegalArgumentException(
                    "Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
        } else {
            try {
                setValue(new SimpleDateFormat(pattern).parse(text));
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }
}
