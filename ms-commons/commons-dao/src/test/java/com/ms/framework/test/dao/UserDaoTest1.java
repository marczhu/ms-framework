package com.ms.framework.test.dao;

import com.ms.framework.dao.account.UserDao;
import com.ms.framework.domain.account.User;

import javax.annotation.Resource;

/**
 * Created by mark.zhu on 2015/12/29.
 */
public class UserDaoTest1 extends BaseDaoTest<UserDao,User> {
    @Resource
    private UserDao userDao;

    @Override
    protected UserDao getDao() {
        return userDao;
    }

    @Override
    protected User getMockedEntity() {
        User user = new User();
        user.setLoginName("user1");
        user.setEmail("user@user.com");
        user.setMobile("187-9987-0099");
        user.setPassword("pwd");
        user.setSalt("salt");
        user.setStatus(User.UserAccountStatus.VALID.getValue());
        user.setDeleted(User.DELETED_NO);
        return user;
    }

    @Override
    public void testCrud() throws Exception {
        super.doCrud();
    }
}
