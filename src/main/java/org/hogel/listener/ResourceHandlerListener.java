package org.hogel.listener;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.hogel.ServerVariable;
import org.hogel.handler.ClassPathResourceHandler;
import org.hogel.handler.FtlResourceHandler;
import org.hogel.handler.ResourceHandler;
import org.hogel.handler.StaticResourceHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ResourceHandlerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        List<ResourceHandler> handlers = ImmutableList.of(
                new FtlResourceHandler(),
                new StaticResourceHandler(),
                new ClassPathResourceHandler()
        );
        ServerVariable.SERVER_RESOURCE_HANDLER.set(context, Optional.of(handlers));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Optional<List<ResourceHandler>> handlers = ServerVariable.SERVER_RESOURCE_HANDLER.get(context);
        for (ResourceHandler handler : handlers.get()) {
            handler.close();
        }
    }
}
