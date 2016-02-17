package com.ms.framework.test.dao;

import com.ms.framework.dao.account.UserDao;
import com.ms.framework.dao.core.paging.PagingBounds;
import com.ms.framework.domain.account.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
public class UserDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);
    @Resource
    private UserDao userDao;

    @Test
    public void testCrud() throws Exception {
        LOGGER.info("test insert...");
        User user = getUserAccountMock();
        long affected = userDao.insert(user);
        LOGGER.info(user.toString());
        Assert.assertTrue(affected == 1);
        Assert.assertTrue(user.getId() == 1);

        User result = userDao.getById(1);
        Assert.assertTrue(result.getId() == 1);

        result = userDao.get(null);
        Assert.assertTrue(result.getId() == 1);
        result = userDao.get(new User());
        Assert.assertTrue(result.getId() == 1);

        String newName = "updated " + new SimpleDateFormat("HHmmss").format(new Date());
        user.setLoginName(newName);
        int updateAffected = userDao.update(user);
        Assert.assertEquals(updateAffected, 1);
        Assert.assertEquals(userDao.getById(user.getId()).getLoginName(), newName);

        long count = userDao.getCount(null);
        Assert.assertEquals(count, 1);

        List<User> userList = userDao.getList(null);
        Assert.assertEquals(userList.size(), 1);

        int deleteAffected = userDao.delete(1);
        Assert.assertEquals(deleteAffected, 1);

        count = userDao.getCount(null);
        Assert.assertEquals(count, 0);

    }

    @Test
    public void testPage() throws Exception {
        for (int i = 0; i < 11; i++) {
           int affected = userDao.insert(getUserAccountMock());
        }
        PagingBounds rowBounds = new PagingBounds(0, 10,true,new PagingBounds.SortEntity("create_time", Sort.Direction.DESC));
        List<User> userList = userDao.getListPage(rowBounds, null);
        LOGGER.info(rowBounds.toString());
        for (User user : userList) {
            LOGGER.info(user.toString());
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
