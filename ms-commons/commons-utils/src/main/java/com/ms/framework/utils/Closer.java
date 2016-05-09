package com.ms.framework.utils;

import java.io.Closeable;

/**
 * Created by mark.zhu on 2016/3/30.
 */
public class Closer {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
            }
        }
    }
}
