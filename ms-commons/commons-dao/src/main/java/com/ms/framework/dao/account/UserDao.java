package com.ms.framework.dao.account;

import com.ms.framework.dao.SimpleCommonDao;
import com.ms.framework.domain.account.User;

/**
 * Created by mark.zhu on 2015/12/1.
 */
public interface UserDao extends SimpleCommonDao<User>{
    public User searchByLoginName(String loginName);
}
