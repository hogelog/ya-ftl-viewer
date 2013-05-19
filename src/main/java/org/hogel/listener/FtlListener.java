package org.hogel.listener;

import com.google.common.base.Optional;
import freemarker.template.Configuration;
import org.hogel.ServerVariable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FtlListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Configuration config = new Configuration();
        config.setDefaultEncoding("UTF-8");
        ServerVariable.FTL_CONFIG.set(context, config);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
