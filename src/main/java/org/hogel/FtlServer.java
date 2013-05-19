package org.hogel;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hogel.listener.FtlListener;
import org.hogel.listener.ResourceHandlerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.IOException;

public class FtlServer {
    private static final Logger LOG = LoggerFactory.getLogger(FtlServer.class);

    public static int DEFAULT_PORT = 9998;

    protected static Server startServer(String[] args) throws Exception {
        ServletContextHandler contextHandler = createContextHandler();
        Optional<ServerConfig> config = loadConfig(args);
        int port;
        if (config.isPresent()) {
            port = config.get().getPort();
            ServletContext servletContext = contextHandler.getServletContext();
            ServerVariable.SERVER_CONFIG.set(servletContext, config);
        } else {
            port = DEFAULT_PORT;
        }
        Server server = new Server(port);
        server.setHandler(contextHandler);
        server.start();
        return server;
    }

    private static Optional<ServerConfig> loadConfig(String[] args) throws IOException {
        if (args.length == 0)
            return Optional.absent();

        Optional<ServerConfig> config = ServerConfig.loadConfig(args[0]);
        return config;
    }

    private static ServletContextHandler createContextHandler() {
        ServletContainer container = new ServletContainer(new PackagesResourceConfig("org.hogel.resource"));
        ServletHolder holder = new ServletHolder(container);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(holder, "/*");

        contextHandler.addEventListener(new FtlListener());
        contextHandler.addEventListener(new ResourceHandlerListener());

        return contextHandler;
    }

    public static void main(String[] args) throws Exception {
        final Server server = startServer(args);
        System.out.println("Type Control-C to stop server.");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                    server.join();
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        });
    }
}
