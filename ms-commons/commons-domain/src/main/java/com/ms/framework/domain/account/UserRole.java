package com.ms.framework.domain.account;

import com.google.common.base.MoreObjects;
import com.ms.framework.domain.BaseDomain;

/**
 * Created by mark.zhu on 2015/12/15.
 */
public class UserRole extends BaseDomain {
    private int userId;
    private int roleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("roleId", roleId)
                .toString();
    }
}
