package org.hogel.listener;

import com.google.common.base.Optional;
import org.hogel.ServerConfig;
import org.hogel.ServerVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class ConfigListener implements ServletContextListener {
    public static String DEFAULT_CONFIG_FILE_NAME = "config.json";

    private static final Logger LOG = LoggerFactory.getLogger(ConfigListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            String configPath = getConfigPath();
            Optional<ServerConfig> serverConfig = ServerConfig.loadConfig(configPath);
            ServerVariable.SERVER_CONFIG.set(context, serverConfig);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    public String getConfigPath() {
        String propertyConfigPath = System.getProperty("ftlviewer.config.path");
        if (propertyConfigPath == null) {
            return DEFAULT_CONFIG_FILE_NAME;
        }
        return propertyConfigPath;
    }
}
