package com.ms.admin.service;

import com.ms.framework.dao.SimpleCommonDao;
import com.ms.framework.dao.core.paging.PageImpl;
import com.ms.framework.dao.core.paging.PagingBounds;
import com.ms.framework.domain.BaseDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by mark.zhu on 2015/12/15.
 */
public abstract class AbstractCommonService<D extends SimpleCommonDao<T>, T extends BaseDomain> implements PagingAndSortingService<T>{

    public int insert(T target) throws Exception {
        return getDao().insert(target);
    }

    public int update(T target) throws Exception {
        return getDao().update(target);
    }

    public int delete(long id) throws Exception {
        return getDao().delete(id);
    }

    public T getById(long id) throws Exception {
        return getDao().getById(id);
    }

    public T get(T condition) throws Exception {
        return getDao().get(condition);
    }

    public List<T> getList(T condition) throws Exception {
        return getDao().getList(condition);
    }

    @Override
    public Page<T> getListPage(Pageable pageable, T condition) throws Exception {
        if (pageable == null) {
            throw new IllegalArgumentException("no page parameters found!");
        }
        List<T> content = getDao().getListPage(PagingBounds.createPagingBounds(pageable), condition);
        long total = getDao().getCount(condition);
        return new PageImpl(content, pageable, total);
    }

    public abstract D getDao();
}
