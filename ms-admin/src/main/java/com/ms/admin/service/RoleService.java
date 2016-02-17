package com.ms.admin.service;

import com.ms.framework.dao.account.RoleDao;
import com.ms.framework.domain.account.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by mark.zhu on 2015/12/15.
 */
public class RoleService extends AbstractCommonService<RoleDao, Role> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDao getDao() {
        return roleDao;
    }

    public Set<String> rolesQuery(long userId) {
        Set<Role> roleSet = roleDao.rolesQuery(userId);
        Set<String> rolesIdNameSet = new HashSet();
        for (Iterator<Role> it = roleSet.iterator(); it.hasNext(); ) {
            Role role = it.next();
            rolesIdNameSet.add(role.getId() + ":" + role.getName());
        }
        return rolesIdNameSet;
    }

}
