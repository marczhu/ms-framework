package com.ms.framework.domain.account;

import com.google.common.base.MoreObjects;

/**
 * Created by mark.zhu on 2015/12/15.
 */
public class RolePermission {
    private int roleId;
    private int permissionId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("roleId", roleId)
                .add("permissionId", permissionId)
                .toString();
    }
}
