package com.ms.training.resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mark.zhu on 2016/1/8.
 */
public class ClassPathResourceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathResourceTest.class);

    @Test
    public void testClassPathResource() throws IOException {
        InputStream in = null;
        try {
            EncodedResource encodedResource = new EncodedResource(new ClassPathResource("sql/schema.sql"),"UTF-8");
            in = encodedResource.getInputStream();
            byte[] b = new byte[in.available()];
            in.read(b);
            LOGGER.info(new String(b));
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignore) {

                }
            }
        }
    }

    @Test
    public void testGetResource() throws IOException {
        InputStream in = null;
        try {
            ClassLoader cl = getDefaultClassLoader();
            in = cl.getResourceAsStream("sql/schema.sql");
            byte[] b = new byte[in.available()];
            in.read(b);
            LOGGER.info(new String(b));
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignore) {

                }
            }
        }
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassPathResourceTest.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }

}
