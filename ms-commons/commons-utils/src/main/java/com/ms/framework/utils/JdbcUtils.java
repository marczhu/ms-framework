package com.ms.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

/**
 * JDBC工具类，如果有数据源，可以使用@see org.springframework.jdbc.datasource.DataSourceUtils
 * Created by mark.zhu on 2015/12/1.
 */
public abstract class JdbcUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtils.class);

    private static final String[] DRIVERS = {
            "h2:", "org.h2.Driver",
            "Cache:", "com.intersys.jdbc.CacheDriver",
            "daffodilDB://", "in.co.daffodil.db.rmi.RmiDaffodilDBDriver",
            "daffodil", "in.co.daffodil.db.jdbc.DaffodilDBDriver",
            "db2:", "com.ibm.db2.jcc.DB2Driver",
            "derby:net:", "org.apache.derby.jdbc.ClientDriver",
            "derby://", "org.apache.derby.jdbc.ClientDriver",
            "derby:", "org.apache.derby.jdbc.EmbeddedDriver",
            "FrontBase:", "com.frontbase.jdbc.FBJDriver",
            "firebirdsql:", "org.firebirdsql.jdbc.FBDriver",
            "hsqldb:", "org.hsqldb.jdbcDriver",
            "informix-sqli:", "com.informix.jdbc.IfxDriver",
            "jtds:", "net.sourceforge.jtds.jdbc.Driver",
            "microsoft:", "com.microsoft.jdbc.sqlserver.SQLServerDriver",
            "mimer:", "com.mimer.jdbc.Driver",
            "mysql:", "com.mysql.jdbc.Driver",
            "odbc:", "sun.jdbc.odbc.JdbcOdbcDriver",
            "oracle:", "oracle.jdbc.driver.OracleDriver",
            "pervasive:", "com.pervasive.jdbc.v2.Driver",
            "pointbase:micro:", "com.pointbase.me.jdbc.jdbcDriver",
            "pointbase:", "com.pointbase.jdbc.jdbcUniversalDriver",
            "postgresql:", "org.postgresql.Driver",
            "sybase:", "com.sybase.jdbc3.jdbc.SybDriver",
            "sqlserver:", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "teradata:", "com.ncr.teradata.TeraDriver",
    };

    private static String getDriver(String url) {
        if (url.startsWith("jdbc:")) {
            url = url.substring("jdbc:".length());
            for (int i = 0; i < DRIVERS.length; i += 2) {
                String prefix = DRIVERS[i];
                if (url.startsWith(prefix)) {
                    return DRIVERS[i + 1];
                }
            }
        }
        return null;
    }

    public static Connection getConnection(String url,
                                           String user, String password) throws SQLException, ClassNotFoundException {
        return getConnection(getDriver(url), url, user, password);
    }

    public static Connection getConnection(String driver, String url,
                                           String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    //    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOGGER.trace("Could not close JDBC ResultSet", ex);
            } catch (Throwable ex) {
                // We don't trust the JDBC driver: It might throw RuntimeException or Error.
                LOGGER.trace("Unexpected exception on closing JDBC ResultSet", ex);
            }
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOGGER.trace("Could not close JDBC Statement", ex);
            } catch (Throwable ex) {
                // We don't trust the JDBC driver: It might throw RuntimeException or Error.
                LOGGER.trace("Unexpected exception on closing JDBC Statement", ex);
            }
        }
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOGGER.trace("Could not close JDBC Connection", ex);
            } catch (Throwable ex) {
                // We don't trust the JDBC driver: It might throw RuntimeException or Error.
                LOGGER.trace("Unexpected exception on closing JDBC Connection", ex);
            }
        }
    }
}
