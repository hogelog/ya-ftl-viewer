package org.hogel.listener;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import net.arnx.jsonic.JSON;
import org.hogel.ServerConfig;
import org.hogel.ServerVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@WebListener
public class ConfigListener implements ServletContextListener {
    public static String DEFAULT_CONFIG_FILE_NAME = "config.json";

    private static final Logger LOG = LoggerFactory.getLogger(ConfigListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            Optional<ServerConfig> serverConfig = ServerConfig.loadConfig(DEFAULT_CONFIG_FILE_NAME);
            ServerVariable.SERVER_CONFIG.set(context, serverConfig);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
