package com.ms.framework.test.dao;

import com.ms.framework.dao.account.UserDao;
import com.ms.framework.dao.core.paging.PageImpl;
import com.ms.framework.dao.core.paging.PagingBounds;
import com.ms.framework.domain.account.User;
import org.apache.ibatis.session.RowBounds;
import org.h2.util.DateTimeUtils;
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
import java.util.*;

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
        user.setStatus(User.STATUS_NORMAL);
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
            int affected = userDao.insert(getMockedEntity());
        }
        int pageSize = 3;

        User user = new User();
        user.setLoginName("use");
        user.setEmail("use");
        user.setMobile("0099");
        user.setStatus(User.STATUS_NORMAL);
        Map condition = new HashMap();
        condition.put("user",user);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND, 0);
        condition.put("beginTime", new Date(c.getTimeInMillis()));
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR,23);
        c1.set(Calendar.MINUTE,59);
        c1.set(Calendar.SECOND, 59);
        condition.put("endTime", new Date(c1.getTimeInMillis()));
        long total = userDao.getCount(condition);
        PageRequest request = new PageRequest(0, pageSize, new Sort(Sort.Direction.DESC, "create_time"));
        //根据总记录数和pageSize计算总页数
        Page<User> page = new PageImpl(new ArrayList(), request, total);

        //获取每一页数据
        for (int i = 0; i < page.getTotalPages(); i++) {
            PageRequest pageRequest = new PageRequest(i, pageSize, new Sort(Sort.Direction.DESC, "create_time"));
            List<User> userList = userDao.getListPage(PagingBounds.createPagingBounds(pageRequest), condition);
            Page<User> actualPage = new PageImpl(userList, pageRequest, total);
            LOGGER.info(actualPage.toString());
            for (User target : actualPage.getContent()) {
                LOGGER.info(target.toString());
            }
        }
    }

    @Test
    public void testTransaction() throws Exception {
        //TODO

    }
    @Test
    public void testFuzzyQuery() throws Exception {
        int affected = userDao.insert(getMockedEntity());
        User condition = new User();
        condition.setLoginName("use");
        condition.setEmail("use");
        condition.setMobile("0099");
        List<User> userList = userDao.getList(condition);
        for (User user : userList) {
            LOGGER.info(user.toString());
        }
    }
}
