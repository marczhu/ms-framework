package com.ms.framework.dao.account;

import com.ms.framework.dao.SimpleCommonDao;
import com.ms.framework.domain.account.Role;

import java.util.Set;

/**
 * Created by mark.zhu on 2015/12/15.
 */
public interface RoleDao extends SimpleCommonDao<Role> {
    public Set<Role> rolesQuery(long userId);
}
