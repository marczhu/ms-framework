package com.ms.framework.test.dao;

import com.ms.framework.dao.SimpleCommonDao;
import com.ms.framework.domain.BaseDomain;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by mark.zhu on 2015/12/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-root.xml")
public abstract class BaseDaoTest<D extends SimpleCommonDao<T>,T extends BaseDomain> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoTest.class);
    protected  abstract D getDao();
    protected  abstract T getMockedEntity();
    @Test
    public  abstract void testCrud() throws Exception;

    protected void doCrud() throws Exception {
        LOGGER.info("test insert...");
        T entity = getMockedEntity();
        entity.setDeleted(T.DELETED_NO);
        long affected = getDao().insert(entity);
        LOGGER.info(entity.toString());
        Assert.assertTrue(affected == 1);
        Assert.assertTrue(entity.getId() == 1);

        T result = getDao().getById(1);
        Assert.assertTrue(result.getId() == 1);

        result = getDao().get(null);
        Assert.assertTrue(result.getId() == 1);

        int deleted = T.DELETED_YES;
        entity.setDeleted(deleted);
        int updateAffected = getDao().update(entity);
        Assert.assertEquals(updateAffected, 1);
        Assert.assertEquals(getDao().getById(entity.getId()).getDeleted(), T.DELETED_YES);

        long count = getDao().getCount(null);
        Assert.assertEquals(count, 1);

        List<T> entityList = getDao().getList(null);
        Assert.assertEquals(entityList.size(), 1);

        int deleteAffected = getDao().delete(1);
        Assert.assertEquals(deleteAffected, 1);

        count = getDao().getCount(null);
        Assert.assertEquals(count, 0);
    }
}
