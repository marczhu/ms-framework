package com.ms.framework.domain.account;

import com.google.common.base.MoreObjects;
import com.ms.framework.domain.BaseDomain;

/**
 * Created by mark.zhu on 2015/11/26.
 */
public class User extends BaseDomain {
    public static final int STATUS_NOT_INIT = 1;
    public static final int STATUS_NORMAL = 2;
    public static final int STATUS_LOCKED = 20;
    private String loginName;
    private String email;
    private String mobile;
    private String password;
    private String salt;
    private int status;
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public enum UserAccountStatus{
        VALID(1),INVALID(-1);
        private int value;
        private UserAccountStatus(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("loginName", loginName)
                .add("email", email)
                .add("mobile", mobile)
                .add("password", password)
                .add("salt", salt)
                .add("status", status)
                .toString();
    }
}
