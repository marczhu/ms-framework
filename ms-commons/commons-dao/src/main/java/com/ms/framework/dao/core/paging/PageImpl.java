package com.ms.framework.dao.core.paging;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * EL表达式无法获取is和has开头的布尔值
 * Created by mark.zhu on 2016/2/16.
 */
public class PageImpl<T> extends org.springframework.data.domain.PageImpl<T> {
    public PageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageImpl(List<T> content) {
        super(content);
    }

    public boolean getIsFirst() {
        return this.isFirst();
    }

    public boolean getIsLast() {
        return this.isLast();
    }

    public boolean getHasPrevious() {
        return this.hasPrevious();
    }

    public boolean getHasNext() {
        return this.hasNext();
    }

    @Override
    public String toString() {
        return super.toString() + "|" + MoreObjects.toStringHelper(this)
                .add("totalPages", this.getTotalPages())
                .add("number", this.getNumber())
                .add("size", this.getSize())
                .add("isFirst", this.isFirst())
                .add("isLast", this.isLast())
                .add("hasPrevious", this.hasPrevious())
                .add("hasNext", this.hasNext())
                .add("totalElements", this.getTotalElements())
                .add("contentSize", this.getContent().size())
                .add("numberOfElements", this.getNumberOfElements())
                .toString();
    }
}
