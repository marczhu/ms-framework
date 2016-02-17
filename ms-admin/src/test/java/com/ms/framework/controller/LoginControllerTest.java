package com.ms.framework.controller;

import com.ms.admin.controller.LoginController;
import com.ms.admin.controller.RestResponse;
import com.ms.framework.domain.account.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by mark.zhu on 2016/1/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath:spring/spring-root.xml"
 })
public class LoginControllerTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new LoginControllerTest()).build();
    }

    @Test
    public void testLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/do_login.action", "loginName=abc", "password=def").accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin2()throws Exception {
        LoginController  loginController = new LoginController();

        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/save.do");
        request.addParameter("url", "index？pageNo=20");
        request.setSession(new MockHttpSession(null));
        HttpServletResponse response = new MockHttpServletResponse();
        User user = new User();
        user.setLoginName("abc");
        user.setPassword("def");
        try {
            RestResponse result = loginController.login(user);
        } catch (Exception e) {
        }
    }
}
