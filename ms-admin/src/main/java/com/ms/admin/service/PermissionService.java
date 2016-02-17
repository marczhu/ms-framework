package com.ms.admin.service;

import com.ms.framework.dao.account.PermissionDao;
import com.ms.framework.domain.account.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mark.zhu on 2015/12/15.
 */
public class PermissionService extends AbstractCommonService<PermissionDao, Permission> {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PermissionDao getDao() {
        return permissionDao;
    }

    public Set<String> permissionsQuery(long userId) {
        List<Permission> permissionList = permissionDao.permissionsQuery(userId);
        Set<String> permissionSet = new HashSet<>();
        for (Permission permission : permissionList) {
            permissionSet.add(permission.getType() + "_" + permission.getResourceId() + ":" + permission.getExpression());
        }
        return permissionSet;
    }

}
