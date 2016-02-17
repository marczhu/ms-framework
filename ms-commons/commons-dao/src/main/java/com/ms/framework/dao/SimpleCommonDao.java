package com.ms.framework.dao;

import com.ms.framework.dao.core.paging.PagingBounds;
import com.ms.framework.domain.BaseDomain;

import java.util.List;

/**
 *
 * User: mark.zhu
 * Date: 14-4-23
 * Time: 上午11:19
 */
public interface SimpleCommonDao<T extends BaseDomain> {
    /**
     *
     * @param target
     * @return affected row count
     * @throws Exception
     */
    int insert(T target) throws Exception;

    /**
     *
     * @param target
     * @return affected row count
     * @throws Exception
     */
    int update(T target) throws Exception;

    /**
     * delete by id
     * @param id
     * @return affected row count
     * @throws Throwable
     */
    int delete(long id) throws Exception;

    /**
     * get by id
     * @param id
     * @return
     * @throws Throwable
     */
    T getById(long id) throws Exception;

    /**
     * get by condition
     * @param condition
     * @return
     * @throws Throwable
     */
    T get(T condition) throws Exception;

    /**
     * select list by condition
     * @param condition
     * @return
     * @throws Throwable
     */
    List<T> getList(T condition) throws Exception;

    /**
     * select paged list by condition
     * @param bounds
     * @param condition
     * @return
     * @throws Exception
     */
    List<T> getListPage(PagingBounds bounds,T condition) throws Exception;

    /**
     *
     * @param obj
     * @return
     */
    long getCount(T obj);
}
