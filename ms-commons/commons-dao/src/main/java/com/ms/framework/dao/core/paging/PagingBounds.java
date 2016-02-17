package com.ms.framework.dao.core.paging;

import com.google.common.base.MoreObjects;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Iterator;

/**
 * 扩展total和sort,只支持一个order属性
 * Created by mark.zhu on 2016/2/15.
 */
public class PagingBounds extends RowBounds {
    private static boolean NEED_GET_TOTAL_DEFAULT = false;
    private int total;
    private int offset;
    private int limit;
    private boolean needGetTotal;
    private SortEntity sortEntity;

    public PagingBounds(int offset, int limit) {
        this(offset, limit, NEED_GET_TOTAL_DEFAULT);
    }

    public PagingBounds(int offset, int limit, boolean needGetTotal) {
        super(offset, limit);
        this.needGetTotal = needGetTotal;
    }

    public PagingBounds(int offset, int limit, boolean needGetTotal, SortEntity sortEntity) {
        super(offset, limit);
        this.needGetTotal = needGetTotal;
        this.sortEntity = sortEntity;
    }

    public void setToDefault() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public static PagingBounds createPagingBounds(Pageable pageable) {
        Sort.Order order = null;
        Iterator<Sort.Order> it = pageable.getSort().iterator();
        //获取第一个,只支持一个order属性
        if (it.hasNext()) {
            order = it.next();
        }
        if (order != null) {
            return new PagingBounds(pageable.getOffset(), pageable.getPageSize(), NEED_GET_TOTAL_DEFAULT, new PagingBounds.SortEntity(order.getProperty(), order.getDirection()));
        } else {
            return new PagingBounds(pageable.getOffset(), pageable.getPageSize());
        }
    }

    public int getSelectCount() {
        return limit + offset;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public boolean isNeedGetTotal() {
        return needGetTotal;
    }

    public void setNeedGetTotal(boolean needGetTotal) {
        this.needGetTotal = needGetTotal;
    }

    public SortEntity getSortEntity() {
        return sortEntity;
    }

    public void setSortEntity(SortEntity sortEntity) {
        this.sortEntity = sortEntity;
    }

    @Override
    public String toString() {
        return super.toString() + MoreObjects.toStringHelper(this)
                .add("total", total)
                .add("offset", offset)
                .add("limit", limit)
                .add("needGetTotal", needGetTotal)
                .add("sortEntity", sortEntity.toString())
                .toString();
    }

    public static class SortEntity {
        private String orderProperty;
        private Sort.Direction direction;

        public SortEntity(String orderProperty, Sort.Direction direction) {
            if (StringUtils.isEmpty(orderProperty) || direction == null) {
                throw new IllegalArgumentException("sort property is null!");
            }
            this.orderProperty = orderProperty;
            this.direction = direction;
        }

        public String getOrderProperty() {
            return orderProperty;
        }

        public Sort.Direction getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            return super.toString() + MoreObjects.toStringHelper(this)
                    .add("orderProperty", orderProperty)
                    .add("direction", direction)
                    .toString();
        }
    }
}
