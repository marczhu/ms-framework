package com.ms.admin.controller.user;

import com.ms.admin.controller.ObjectErrorHelper;
import com.ms.admin.controller.RestResponse;
import com.ms.admin.controller.validator.UserValidator;
import com.ms.admin.exception.BusinessException;
import com.ms.admin.exception.SystemException;
import com.ms.admin.service.UserService;
import com.ms.framework.dao.core.paging.PageImpl;
import com.ms.framework.domain.account.User;
import com.ms.framework.utils.DateHelper;
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

import java.util.*;

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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
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
            //TODO token防止客户多次点击button重复提交或者refresh页面导致重复提交(前端js控制+后端token)
            return new ModelAndView("redirect:/user/list_async.action");
        }
    }

    @RequestMapping(value = "/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView list(@RequestParam(value = "pageno", defaultValue = "0", required = false) int pageno,
                             @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                             @RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
                             @RequestParam(value = "sort", defaultValue = "create_time", required = false) String sortProperty, Date beginTime, Date endTime, final User user) throws SystemException {
        Page<User> page;
        try {
            if (size > PageImpl.DEFAULT_MAX_PAGE_SIZE) {
                throw new IllegalArgumentException("size must not large than " + PageImpl.DEFAULT_MAX_PAGE_SIZE);
            }
            Sort sort = new Sort(direction, sortProperty);
            Map condition = new HashMap();
            condition.put("user", user);
            condition.put("beginTime", beginTime);
            //set time to 23:59:59.999
            if (endTime != null) {
                endTime = DateHelper.toMaxTimeOfDay(endTime);
            }
            condition.put("endTime", endTime);
            page = userService.getListPage(new PageRequest(pageno, size, sort), condition);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknown_exception.action");
        }
        ModelAndView modelAndView = new ModelAndView(USER_LIST_PAGE, "page", page);
        modelAndView.addObject("user", user);
        modelAndView.addObject("beginTime", beginTime);
        modelAndView.addObject("endTime", endTime);
        return modelAndView;
    }

    @RequestMapping(value = "/list_async.action", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listAsyncPage() throws SystemException {
        return new ModelAndView("/user/user_list_async");
    }

    @RequestMapping(value = "/list_async_data.action", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listAsync(@RequestParam(value = "pageno", defaultValue = "0", required = false) int pageno,
                             @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                             @RequestParam(value = "order", defaultValue = "DESC", required = false) Sort.Direction direction,
                             @RequestParam(value = "sort", defaultValue = "create_time", required = false) String sortProperty, Date beginTime, Date endTime, final User user) throws SystemException {
        Page<User> page;
        try {
            if (size > PageImpl.DEFAULT_MAX_PAGE_SIZE) {
                throw new IllegalArgumentException("size must not large than " + PageImpl.DEFAULT_MAX_PAGE_SIZE);
            }
            Sort sort = new Sort(direction, sortProperty);
            Map condition = new HashMap();
            condition.put("user", user);
            condition.put("beginTime", beginTime);
            //set time to 23:59:59.999
            if (endTime != null) {
                endTime = DateHelper.toMaxTimeOfDay(endTime);
            }
            condition.put("endTime", endTime);
            page = userService.getListPage(new PageRequest(pageno, size, sort), condition);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknown_exception.action");
        }
        ModelAndView modelAndView = new ModelAndView("/user/user_list_async_data", "page", page);
        return modelAndView;
    }

    @RequestMapping(value = "/update.action", method = RequestMethod.GET)
    public ModelAndView update(final long id) {
        User user;
        try {
            user = userService.getById(id);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknown_exception.action");
        }
        return new ModelAndView(USER_UPDATE_PAGE,"user",user);
    }

    @RequestMapping(value = "/do_update.action", method = RequestMethod.POST)
    public ModelAndView doUpdate(final User formUser) {
        try {
            User user = userService.getById(formUser.getId());
            if (formUser.getLoginName() != null &&  !"".equals(formUser.getLoginName())) {
                user.setLoginName(formUser.getLoginName());
            }
            if (formUser.getEmail() != null &&  !"".equals(formUser.getEmail())) {
                user.setEmail(formUser.getEmail());
            }
            if (formUser.getMobile() != null &&  !"".equals(formUser.getMobile())) {
                user.setMobile(formUser.getMobile());
            }
            if (formUser.getStatus() != 0) {
                user.setStatus(formUser.getStatus());
            }
            userService.update(user);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return new ModelAndView("redirect:/unknown_exception.action");
        }
        return new ModelAndView("redirect:/user/list.action");
    }

    @RequestMapping(value = "/do_update_async.action", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse doUpdateAsync(final User formUser) {
        RestResponse.ResponseBuilder builder = RestResponse.builder();
        try {
            User user = userService.getById(formUser.getId());
            if (formUser.getLoginName() != null &&  !"".equals(formUser.getLoginName())) {
                user.setLoginName(formUser.getLoginName());
            }
            if (formUser.getEmail() != null &&  !"".equals(formUser.getEmail())) {
                user.setEmail(formUser.getEmail());
            }
            if (formUser.getMobile() != null &&  !"".equals(formUser.getMobile())) {
                user.setMobile(formUser.getMobile());
            }
            if (formUser.getStatus() != 0) {
                user.setStatus(formUser.getStatus());
            }
            userService.update(user);
        } catch (Throwable throwable) {
            LOGGER.error(String.format(SystemException.UnknownException.LOG_MESSAGE_PREFIX + "获取数据异常"), throwable);
            return builder.status(HttpStatus.BAD_REQUEST).entity(SystemException.UnknownException.DEFAULT_MESSAGE).build();
        }
        return builder.ok().build();
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
}
