package org.hogel.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextCatchListener implements ServletContextListener {
    public static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = null;
    }
}
