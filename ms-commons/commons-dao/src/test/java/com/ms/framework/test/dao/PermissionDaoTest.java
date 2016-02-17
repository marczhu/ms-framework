package com.ms.framework.test.dao;

import com.ms.framework.dao.account.PermissionDao;
import com.ms.framework.domain.account.Permission;

import javax.annotation.Resource;

/**
 * Created by mark.zhu on 2015/12/29.
 */
public class PermissionDaoTest extends BaseDaoTest<PermissionDao,Permission> {

    @Resource
    private PermissionDao permissionDao;
    @Override
    protected PermissionDao getDao() {
        return permissionDao;
    }

    @Override
    protected Permission getMockedEntity() {
        Permission permission = new Permission();
        permission.setType(Permission.PERMISSION_TYPE_MENU_RESOURCE);
        permission.setResourceId(1);
        permission.setExpression("expression");
        permission.setRemark("permission remark");
        return permission;
    }

    @Override
    public void testCrud() throws Exception {
        super.doCrud();
    }
}
