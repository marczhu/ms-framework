package com.ms.admin.shiro;

import com.ms.admin.shiro.security.PasswordService;
import com.ms.framework.domain.account.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * 重写{@link org.apache.shiro.authc.credential.PasswordMatcher},只支持文本，不支持存储以及解析HashFormat，credentials直接使用User实体
 * Created by mark.zhu on 2015/12/15.
 */
public class TextHashingPasswordMatcher implements CredentialsMatcher {

    private PasswordService passwordService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        char[] submittedPassword = (char[]) token.getCredentials();
        User storedCredentials = (User) info.getCredentials();
        return passwordService.passwordsMatch(new String(submittedPassword), storedCredentials);
    }

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }
}
