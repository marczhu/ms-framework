package com.h2.server;

import org.h2.tools.Server;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Auto create h2 database server and manually create h2 database server
 * <p/>
 * Created by mark.zhu on 2015/11/9.
 */
public class EmbeddedServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedServerTest.class);
    private static final String SQL = "select 1";
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";

    @Test
    public void testAutoCreateServer() throws ClassNotFoundException, SQLException {
        String jdbcUrl = "jdbc:h2:~/h2/test;MODE=MySQL;AUTO_RECONNECT=TRUE;PAGE_SIZE=16384;TRACE_LEVEL_FILE=4";
        Connection conn = null;
        try {
            conn = getConnection(jdbcUrl);
            LOGGER.info("column count:" + getColumnCount(conn));
        } finally {
            closeConnection(conn);
        }
    }

    @Test
    public void testAutoCreateInMemoryServer() throws ClassNotFoundException, SQLException {
        String jdbcUrl = "jdbc:h2:mem:test";
        Connection conn = null;
        try {
            conn = getConnection(jdbcUrl);
            LOGGER.info("column count:" + getColumnCount(conn));
        } finally {
            closeConnection(conn);
        }
    }

    @Test
    public void testManuallyCreateServer() throws SQLException, ClassNotFoundException {
        String jdbcUrl = "jdbc:h2:tcp://localhost:9123/~/h2/trip8;MODE=MySQL;AUTO_RECONNECT=TRUE;PAGE_SIZE=16384;TRACE_LEVEL_FILE=4";
        Server server = null;
        Connection conn = null;
        try {
            server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();
            conn = getConnection(jdbcUrl);
            LOGGER.info("column count:" + getColumnCount(conn));
        } finally {
            closeConnection(conn);
            if (server != null) {
                server.stop();
            }
        }
    }

    private int getColumnCount(Connection conn) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(SQL);
            return rs.getMetaData().getColumnCount();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Could not close JDBC ResultSet", ex);
                    }
                } catch (Throwable ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Unexpected exception on closing JDBC ResultSet", ex);
                    }
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Could not close JDBC Statement", ex);
                    }
                } catch (Throwable ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Unexpected exception on closing JDBC Statement", ex);
                    }
                }
            }
        }
    }

    private Connection getConnection(String jdbcUrl) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS_NAME);
        return DriverManager.getConnection(jdbcUrl, "sa", "");
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Could not close JDBC Connection", ex);
                }
            } catch (Throwable ex) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Unexpected exception on closing JDBC Connection", ex);
                }
            }
        }
    }
}
