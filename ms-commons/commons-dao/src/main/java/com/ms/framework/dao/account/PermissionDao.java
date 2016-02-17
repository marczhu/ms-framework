package com.ms.framework.dao.account;

import com.ms.framework.dao.SimpleCommonDao;
import com.ms.framework.domain.account.Permission;

import java.util.List;
import java.util.Set;


/**
 * Created by mark.zhu on 2015/12/15.
 */
public interface PermissionDao extends SimpleCommonDao<Permission>{
    public List<Permission> permissionsQuery(long userId);

}
