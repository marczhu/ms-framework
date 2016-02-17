package com.ms.admin.service;

import com.ms.admin.shiro.security.PasswordService;
import com.ms.framework.dao.account.UserDao;
import com.ms.framework.domain.account.User;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by mark.zhu on 2015/12/3.
 */
public class UserService extends AbstractCommonService<UserDao, User> implements PagingAndSortingService<User> {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserDao userDao;

    @Override
    public int insert(User user) throws Exception {
        //TODO 捕获唯一索引异常，并封装异常，以便Controller处理异常
        User encrypted = passwordService.encryptPassword(user.getPassword());
        user.setPassword(encrypted.getPassword());
        user.setSalt(encrypted.getSalt());
        user.setStatus(User.STATUS_NORMAL);
        user.setDeleted(User.DELETED_NO);
        return userDao.insert(user);
    }

    @Override
    public UserDao getDao() {
        return userDao;
    }

    /**
     * 登陆
     *
     * @param loginName
     * @param password
     * @return
     * @throws Exception
     * @De
     * @deprecated 改为使用{@link com.ms.admin.shiro.TextHashingPasswordMatcher}
     */
    @Deprecated
    public void login(String loginName, String password) throws Exception {
        User user = new User();
        user.setLoginName(loginName);
        user = userDao.get(user);
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getStatus() == User.STATUS_LOCKED) {
            throw new LockedAccountException();
        }
        if (!passwordService.passwordsMatch(password, user)) {
            throw new IncorrectCredentialsException();
        }
    }

    public User searchByLoginName(String loginName) throws Exception {
        return userDao.searchByLoginName(loginName);
    }
}
