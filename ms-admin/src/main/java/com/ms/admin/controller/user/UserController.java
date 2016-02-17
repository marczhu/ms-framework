package com.ms.admin.controller.user;

import com.ms.admin.controller.ObjectErrorHelper;
import com.ms.admin.controller.RestResponse;
import com.ms.admin.controller.validator.UserValidator;
import com.ms.admin.exception.BusinessException;
import com.ms.admin.exception.SystemException;
import com.ms.admin.service.UserService;
import com.ms.framework.domain.account.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * Created by mark.zhu on 2015/12/1.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    public static final String USER_CREATE_PAGE = "/user/user_create";
    public static final String USER_LIST_PAGE = "/user/user_list";
    public static final String USER_UPDATE_PAGE = "/user/user_update";

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    // TODO 查看何时调用
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/create.action")
    public String create() {
        return USER_CREATE_PAGE;
    }

    @RequestMapping(value = "/do_create.action", method = RequestMethod.POST)
    public ModelAndView doCreate(final User user, BindingResult result) {
        try {
            userService.insert(user);
        } catch (BusinessException e) {
            result.addError(ObjectErrorHelper.createDefaultError(e.getMessage()));
        } catch (SystemException e) {
            result.addError(ObjectErrorHelper.createDefaultError(e.getMessage()));
        } catch (Throwable e) {
            result.addError(ObjectErrorHelper.DEFAULT_UNKNOWN_OBJECT_ERROR);
            LOGGER.error(e.getMessage(), e);
        }
        if (result.hasErrors()) {
            return new ModelAndView(USER_CREATE_PAGE);
        } else {
            //TODO token防止客户多次点击重复提交(前端js控制+后端token)
            return new ModelAndView("redirect:/user/list.action");
        }
    }

    @RequestMapping(value = "/is_valid_login_name.action", method = RequestMethod.GET)
    @ResponseBody
    public boolean isValidLoginName(final String loginName) throws SystemException {
        try {
            User user = userService.searchByLoginName(loginName);
            if (user == null) {
                return true;
            }
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "校验用户名失败,用户名:%s", loginName), throwable);
            throw new SystemException.UnknownException();
        }
        return false;
    }

    @RequestMapping(value = "/list.action", method = RequestMethod.GET)
    public ModelAndView list(final User condition) throws SystemException {
        List<User> users;
        try {
            users = userService.getList(condition);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknow_exception.action");
        }
        return new ModelAndView(USER_LIST_PAGE, "users", users);
    }

    @RequestMapping(value = "/list_page.action", method = RequestMethod.GET)
    public ModelAndView listPage(@RequestParam(value = "pageno", defaultValue = "0", required = false) int pageno,
                                 @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                 @RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
                                 @RequestParam(value = "sort", defaultValue = "create_time", required = false) String sortProperty, final User condition) throws SystemException {
        Page<User> page;
        try {

            page = userService.getListPage(new PageRequest(pageno, size, new Sort(direction, sortProperty)), condition);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknow_exception.action");
        }
        return new ModelAndView(USER_LIST_PAGE, "page", page);
    }

    @RequestMapping(value = "/do_update.action", method = RequestMethod.POST)
    public ModelAndView doUpdate(final User formUser) {
        try {
            User user = userService.getById(formUser.getId());
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknow_exception.action");
        }
        return new ModelAndView("redirect:/user/list.action");
    }

    @RequestMapping(value = "/delete.action", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse doDelete(final long userId) {
        RestResponse.ResponseBuilder builder = RestResponse.builder();
        try {
            userService.delete(userId);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return builder.status(HttpStatus.BAD_REQUEST).entity(SystemException.UnknownException.DEFAULT_MESSAGE).build();
        }
        return builder.ok().build();
    }

}
