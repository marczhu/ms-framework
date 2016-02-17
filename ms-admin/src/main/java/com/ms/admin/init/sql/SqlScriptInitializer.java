package com.ms.admin.init.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mark.zhu on 2015/12/1.
 */

public class SqlScriptInitializer implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlScriptInitializer.class);
    private static final String DEFAULT_SCRIPT_FILE_NAME = "sql/import.sql";
    private DataSource dataSource;
    private List<String> locations;

    public SqlScriptInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(dataSource, "DataSource must be provided");
        runScripts(getScripts());
    }

    private List<Resource> getScripts() {
        ArrayList<Resource> resources = new ArrayList();
        if (locations == null) {
            resources.add(new EncodedResource(new ClassPathResource(DEFAULT_SCRIPT_FILE_NAME), "UTF-8").getResource());
        } else {
            for (String location : locations) {
                resources.add(new EncodedResource(new ClassPathResource(location), "UTF-8").getResource());
            }
        }
        return resources;
    }

    private void runScripts(List<Resource> resources) {
        if (!resources.isEmpty()) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.setContinueOnError(true);
            populator.setSqlScriptEncoding("UTF-8");
            Iterator var3 = resources.iterator();
            while (var3.hasNext()) {
                Resource resource = (Resource) var3.next();
                populator.addScript(resource);
            }
            DatabasePopulatorUtils.execute(populator, this.dataSource);
            //ScriptUtils.executeSqlScript(conn, new EncodedResource(new ClassPathResource("import.sql"), "UTF-8"));
        }
    }
}
