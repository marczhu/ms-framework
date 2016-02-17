package com.ms.mdd.domain.account;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mark.zhu on 2015/11/30.
 */
@Entity(name = "user_account")
public class UserAccount implements Serializable {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    protected long id;

    @Column(nullable = false)
    private String loginName;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String mobile;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String salt;
    @Column(nullable = false)
    private int status;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    protected Date createTime;
    /**
     * 更新时间
     */
    @Column(nullable = false)
    protected Date lastUpdateTime;
    /**
     * 删除标识
     */
    @Column(nullable = false)
    protected int deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", deleted=" + deleted +
                '}';
    }
}
