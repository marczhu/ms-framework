package com.ms.framework.security;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mark.zhu on 2015/12/8.
 */
public class ShiroSecurityTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroSecurityTest.class);

    @Test
    public void testDefaultPasswordService(){
        PasswordService passwordService = new DefaultPasswordService();
        String encrypted = passwordService.encryptPassword("12345");
        LOGGER.info("encrypted: " + encrypted);
        Assert.assertTrue(passwordService.passwordsMatch("12345", encrypted));
    }
}
