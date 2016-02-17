package com.ms.framework.dao.core.paging;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mark.zhu on 2016/2/15.
 */
public abstract class AbstractPagingInterceptor implements Interceptor {

    private static final String INTERCEPTED_SQL_ID_PATTERN = ".*Page$";

    private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");

    private static final String FIELD_DELEGATE = "delegate";

    private static final String FIELD_ROWBOUNDS = "rowBounds";

    private static final String FIELD_MAPPEDSTATEMENT = "mappedStatement";

    private static final String FIELD_SQL = "sql";

    private static final String BLANK = " ";

    protected static final String SELECT = "select";

    protected static final String FROM = "from";

    protected static final String ORDER_BY = "order by";

    protected static final String UNION = "union";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) readField(routingStatementHandler, FIELD_DELEGATE);
        MappedStatement mappedStatement = (MappedStatement) readField(statementHandler, FIELD_MAPPEDSTATEMENT);
        if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
            //TODO 注入的targetSqlIdPattern不生效
            /**拦截特定的sqlId*/
            if (mappedStatement.getId().matches(INTERCEPTED_SQL_ID_PATTERN)) {
                PagingBounds pagingBounds = (PagingBounds) readField(statementHandler, FIELD_ROWBOUNDS);
                BoundSql boundSql = statementHandler.getBoundSql();
                //replace all blank
                String targetSql = replaceSqlBlank(boundSql.getSql());
                //生成并设置分页SQL
                String pagingSql = getSelectPagingSql(targetSql, pagingBounds);
                writeDeclaredField(boundSql, FIELD_SQL, pagingSql);

                //获取总记录数
                if (pagingBounds.isNeedGetTotal()) {
                    Connection connection = (Connection) invocation.getArgs()[0];
                    int total = getTotal(targetSql, boundSql, mappedStatement, connection);
                    pagingBounds.setTotal(total);
                }
                //ensure set to default
                pagingBounds.setToDefault();
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler) {
            try {
                Field delegate = getField(RoutingStatementHandler.class, FIELD_DELEGATE);
                StatementHandler handler = (StatementHandler) delegate.get(target);
                RowBounds rowBounds = (RowBounds) readField(handler, FIELD_ROWBOUNDS);
                if (rowBounds != RowBounds.DEFAULT && rowBounds instanceof PagingBounds) {
                    return Plugin.wrap(target, this);
                }
            } catch (IllegalAccessException e) {
                // ignore
            }
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }


    private int getTotal(String targetSql, BoundSql boundSql, MappedStatement mappedStatement, Connection connection) throws SQLException {
        int total = 0;
        String totalSql = getSelectTotalSql(targetSql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(), totalSql, parameterMappings, parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, totalBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(totalSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return total;
    }

    protected abstract String getSelectTotalSql(String targetSql);

    protected abstract String getSelectPagingSql(String targetSql, PagingBounds pagingBounds);

    private String replaceSqlBlank(String originalSql) {
        Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
        return matcher.replaceAll(BLANK);
    }

    private void writeDeclaredField(Object target, String fieldName, Object value)
            throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
        }
        field.set(target, value);
    }

    private Object readField(Object target, String fieldName)
            throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field.get(target);
    }

    private static Field getField(final Class<?> cls, String fieldName) {
        for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
            try {
                Field field = acls.getDeclaredField(fieldName);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                    return field;
                }
            } catch (NoSuchFieldException ex) {
                // ignore
            }
        }
        return null;
    }

}
