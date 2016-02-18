package com.ms.framework.test.dao;

import com.ms.framework.dao.account.UserDao;
import com.ms.framework.dao.core.paging.PageImpl;
import com.ms.framework.dao.core.paging.PagingBounds;
import com.ms.framework.domain.account.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * dao测试
 * User: mark.zhu
 * Date: 14-4-23
 * Time: 下午2:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-root.xml")
public class UserDaoTest extends BaseDaoTest<UserDao,User>{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);
    @Resource
    private UserDao userDao;

    @Override
    protected UserDao getDao() {
        return userDao;
    }

    @Override
    protected User getMockedEntity() {
        User user = new User();
        user.setLoginName("user1" + new Random().nextInt(Integer.MAX_VALUE));
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

    @Test
    public void testPage() throws Exception {
        //prepare data
        for (int i = 0; i < 11; i++) {
            int affected = userDao.insert(getUserAccountMock());
        }

        int pageSize = 3;
        PageRequest request = new PageRequest(0, 3, new Sort(Sort.Direction.DESC, "create_time"));
        long total = userDao.getCount(null);
        //根据总记录数和pageSize计算总页数
        Page<User> page = new PageImpl(new ArrayList(), request, total);

        //获取每一页数据
        for (int i = 0; i < page.getTotalPages(); i++) {
            PageRequest pageRequest = new PageRequest(i, pageSize, new Sort(Sort.Direction.DESC, "create_time"));
            List<User> userList = userDao.getListPage(PagingBounds.createPagingBounds(pageRequest), null);
            Page<User> actualPage = new PageImpl(userList, pageRequest, total);
            LOGGER.info(actualPage.toString());
            for (User user : actualPage.getContent()) {
                LOGGER.info(user.toString());
            }
        }
    }
    private User getUserAccountMock() {
        User user = new User();
        user.setLoginName("user1" + new Random().nextInt(Integer.MAX_VALUE));
        user.setEmail("user@user.com");
        user.setMobile("187-9987-0099");
        user.setPassword("pwd");
        user.setSalt("salt");
        user.setStatus(User.UserAccountStatus.VALID.getValue());
        user.setDeleted(User.DELETED_NO);
        return user;
    }

}
