package com.ms.framework.test.dao;

import com.ms.framework.dao.account.RoleDao;
import com.ms.framework.domain.account.Role;

import javax.annotation.Resource;

/**
 * Created by mark.zhu on 2015/12/29.
 */
public class RoleDaoTest extends BaseDaoTest<RoleDao, Role> {

    @Resource
    private RoleDao roleDao;

    @Override
    protected RoleDao getDao() {
        return roleDao;
    }

    @Override
    protected Role getMockedEntity() {
        Role role = new Role();
        role.setName("admin");
        role.setRemark("remark:admin");
        return role;
    }

    @Override
    public void testCrud() throws Exception {
        super.doCrud();
    }
}
