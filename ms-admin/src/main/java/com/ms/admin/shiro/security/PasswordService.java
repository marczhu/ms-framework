package com.ms.admin.shiro.security;

import com.ms.framework.domain.account.User;

/**
 * 重新定义接口{@link org.apache.shiro.authc.credential.PasswordService} 满足扩展需要
 * Created by mark.zhu on 2015/12/9.
 */
public interface PasswordService {
    /**
     * 加密
     *
     * @param plaintextPassword 用户输入密码
     * @return
     * @throws IllegalArgumentException
     */
    User encryptPassword(String plaintextPassword) throws IllegalArgumentException;

    /**
     * @param plaintextPassword 用户输入密码
     * @param user       数据库存储的加密后的密码
     * @return
     */
    boolean passwordsMatch(String plaintextPassword, User user);
}
