package com.ms.admin.shiro;

import com.ms.admin.service.PermissionService;
import com.ms.admin.service.RoleService;
import com.ms.admin.service.UserService;
import com.ms.framework.domain.account.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by mark.zhu on 2015/12/3.
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null userId are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        Long userId = (Long) getAvailablePrincipal(principals);

        Set<String> roles;
        Set<String> permissions;
        try {
            // Retrieve roles and permissions from database
            roles = roleService.rolesQuery(userId);
            permissions = permissionService.permissionsQuery(userId);
        } catch (Exception e) {
            final String message = "There was an error while authorizing user [" + userId + "]";
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(message, e);
            }

            // Rethrow any errors as an authorization exception
            throw new AuthorizationException(message, e);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(permissions);
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String loginName = upToken.getUsername();
        // Null username is invalid
        if (loginName == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        SimpleAuthenticationInfo info;
        try {
//            userAccountService.login(username, new String(upToken.getPassword()));
            User user = userService.searchByLoginName(loginName);
            if (user == null) {
                throw new UnknownAccountException();
            }
            if (user.getStatus() == User.STATUS_LOCKED) {
                throw new LockedAccountException();
            }
            info = new SimpleAuthenticationInfo(user.getId(), user, getName());
        } catch (AuthenticationException e) {
            throw e;
        } catch (Throwable e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage(), e);
            }
            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(e);
        }
        return info;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }
}
