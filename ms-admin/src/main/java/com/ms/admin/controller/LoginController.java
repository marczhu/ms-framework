package com.ms.admin.controller;

import com.ms.admin.controller.validator.UserValidator;
import com.ms.admin.exception.SystemException;
import com.ms.admin.service.UserService;
import com.ms.admin.util.AESProvider;
import com.ms.framework.domain.account.User;
import com.ms.framework.utils.AES;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by mark.zhu on 2015/11/26.
 */

@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static String LOGIN_PAGE = "login";
    private static String LOGIN_SUCCESS_PAGE = "login_success";

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private AESProvider aesProvider;

    @RequestMapping("/login.action")
    public String loginPage() {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = "/do_login.action", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse login(final User user) {
        RestResponse.ResponseBuilder builder = RestResponse.builder();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPassword());
        try {
            SecurityUtils.getSubject().login(token);
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
        } catch (UnknownAccountException uae) {
            builder.error("There is no user with username of " + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            builder.error("Password for account " + token.getPrincipal() + " was incorrect!");
        } catch (LockedAccountException lae) {
            builder.error("The account for username " + token.getPrincipal() + " is locked.  " +
                    "Please contact your administrator to unlock it.");
        } catch (ExcessiveAttemptsException ex) {
            builder.error("reached max attempts!");
        } catch (AuthenticationException e) {
            LOGGER.error("Error authenticating.", e);
            builder.error("unknown error occurred. Please try later!");
        }
        if (builder.hasError()) {
            return builder.status(HttpStatus.BAD_REQUEST).entity(user).build();
        } else {
            return builder.ok().build();
//            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/encrypt_cookie.action", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse encrypt(final String password) {
        RestResponse.ResponseBuilder builder = RestResponse.builder();
        try {
            if (password == null) {
                throw new IllegalArgumentException();
            }
            String result = aesProvider.encrypt(password);
            return builder.ok().entity(result).build();
        } catch (Throwable throwable) {
            LOGGER.error("", throwable);
            return builder.error(SystemException.UnknownException.DEFAULT_MESSAGE).build();
        }
    }

    @RequestMapping(value = "/decrypt_cookie.action", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse decrypt(final String loginName, final String password) {
        RestResponse.ResponseBuilder builder = RestResponse.builder();
        try {
            if (loginName == null || password == null) {
                throw new IllegalArgumentException();
            }
            String result = aesProvider.decrypt(password);
            return builder.ok().entity(result).build();
        } catch (Throwable throwable) {
            LOGGER.error("", throwable);
            return builder.error(SystemException.UnknownException.DEFAULT_MESSAGE).build();
        }
    }

    @RequestMapping("/logout.action")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return LOGIN_PAGE;
    }
}
