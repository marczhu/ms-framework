package com.ms.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * provide additional methods to retrieve entities using the pagination and
 * sorting abstraction.
 * see {@link org.springframework.data.repository.PagingAndSortingRepository}
 * Created by mark.zhu on 2016/2/14.
 */
public interface PagingAndSortingService<T> {
    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @param condition
     * @return a page of entities
     */
    Page<T> getListPage(Pageable pageable,T condition) throws Exception;
}