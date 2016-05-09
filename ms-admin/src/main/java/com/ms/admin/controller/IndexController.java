package com.ms.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mark.zhu on 2016/1/18.
 */
@Controller

public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    private static String ERROR_UNKNOW_EXCEPTION_PAGE = "index";
    private static String INDEX_PAGE = "index";
    private static String LEFT_MENU_PAGE = "left_menu";
    private static String TOP_NAV_PAGE = "top_navbar";

    @RequestMapping("/index")
    public String index() {
        return INDEX_PAGE;
    }

    @RequestMapping("/left_menu")
    public ModelAndView leftMenu() {
        return new ModelAndView(LEFT_MENU_PAGE);
    }

    @RequestMapping("/top_nav")
    public ModelAndView topNav() {
        return new ModelAndView(TOP_NAV_PAGE);
    }

    @RequestMapping("/unknown_exception")
    public ModelAndView unknownException(final String message, final String redirectUrl) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("message", message);
        paramsMap.put("url", redirectUrl);
        return new ModelAndView(ERROR_UNKNOW_EXCEPTION_PAGE, paramsMap);
    }

}
