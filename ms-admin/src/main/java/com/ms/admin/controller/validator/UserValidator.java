package com.ms.admin.controller.validator;

import com.ms.framework.domain.account.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by mark.zhu on 2015/12/1.
 */
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o == null){
            errors.rejectValue("errMsg", "userAccount.invalid", "User is null");
            return;
        }
        User target = (User)o;
        if (target.getLoginName() == null || target.getLoginName().isEmpty()) {
            errors.rejectValue("loginName", "userAccount.loginName.invalid", "loginName is null");
            return;
        }
        if (target.getPassword()== null || target.getPassword().isEmpty()) {
            errors.rejectValue("password", "userAccount.password.invalid", "password is invalid");
            return;
        }
    }
}
