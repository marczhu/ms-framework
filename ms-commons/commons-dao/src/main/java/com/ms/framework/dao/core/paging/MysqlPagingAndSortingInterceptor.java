package com.ms.framework.dao.core.paging;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;

/**
 * 支持分页和排序
 * Created by mark.zhu on 2016/2/15.
 */

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}))
public class MysqlPagingAndSortingInterceptor extends AbstractPagingInterceptor {

    @Override
    protected String getSelectTotalSql(String targetSql) {
        String sql = targetSql.toLowerCase();
        StringBuilder sqlBuilder = new StringBuilder(sql);
        deleteSort(sqlBuilder);
        sqlBuilder.insert(0, "select count(1) as _count from ( ").append(" )");
        return sqlBuilder.toString();
    }

    @Override
    protected String getSelectPagingSql(String targetSql, PagingBounds pagingBounds) {
        StringBuilder sqlBuilder = new StringBuilder(targetSql);
        //替换sort
        if (pagingBounds.getSortEntity() != null) {
            replaceOrder(pagingBounds.getSortEntity(),sqlBuilder);
        }

        sqlBuilder.append(LIMIT);
        sqlBuilder.append(pagingBounds.getOffset());
        sqlBuilder.append(",");
        sqlBuilder.append(pagingBounds.getLimit());
        return sqlBuilder.toString();
    }

    private void replaceOrder(PagingBounds.SortEntity sortEntity, StringBuilder sqlBuilder){
        deleteSort(sqlBuilder);
        appendSort(sortEntity,sqlBuilder);
    }
    /**
     * 删除order
     *
     * @param sqlBuilder
     */
    private void deleteSort(StringBuilder sqlBuilder) {
        int orderByPos = sqlBuilder.lastIndexOf(ORDER_BY);
        if (orderByPos != -1) {
            sqlBuilder.delete(orderByPos, sqlBuilder.length());
        }
    }

    private void appendSort(PagingBounds.SortEntity sortEntity, StringBuilder sqlBuilder) {
        sqlBuilder.append("order by ").append(sortEntity.getOrderProperty()).append(" ").append(sortEntity.getDirection().name().toLowerCase());
    }
}
