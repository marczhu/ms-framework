package com.ms.sql;

import com.h2.server.EmbeddedServerTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mark.zhu on 2015/11/30.
 */
public class SqlScriptExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedServerTest.class);
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver";
    @Test
    public void testExecute() throws SQLException, ClassNotFoundException {
        String jdbcUrl = "jdbc:h2:tcp://localhost/~/h2/test;MODE=MySQL;AUTO_RECONNECT=TRUE;PAGE_SIZE=16384;TRACE_LEVEL_FILE=4";
        Connection conn = null;
        try {
            conn = getConnection(jdbcUrl);
            ScriptUtils.executeSqlScript(conn,new EncodedResource(new ClassPathResource("import.sql"),"UTF-8"));
            LOGGER.info("execute finished!");
        } finally {
            closeConnection(conn);
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
