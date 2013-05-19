package org.hogel.listener;

import com.google.common.collect.ImmutableList;
import org.hogel.ServerVariable;
import org.hogel.handler.FtlResourceHandler;
import org.hogel.handler.ResourceHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ResourceHandlerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        List<FtlResourceHandler> handlers = ImmutableList.of(
                new FtlResourceHandler()
        );
        ServerVariable.SERVER_RESOURCE_HANDLER.set(context, handlers);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        List<ResourceHandler> handlers = ServerVariable.SERVER_RESOURCE_HANDLER.get(context);
        for (ResourceHandler handler : handlers) {
            handler.close();
        }
    }
}
