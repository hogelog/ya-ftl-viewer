package org.hogel.resource;

import com.google.common.base.Optional;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.hogel.ServerConfig;
import org.hogel.ServerVariable;
import org.hogel.listener.ConfigListener;
import org.hogel.listener.ContextCatchListener;
import org.hogel.listener.FtlListener;
import org.hogel.listener.ResourceHandlerListener;
import org.junit.After;
import org.junit.Before;

import javax.servlet.ServletContext;

public abstract class ResourceTestCase {
    public static class JerseyTestWrapper extends JerseyTest {
        public JerseyTestWrapper(WebAppDescriptor descriptor) {
            super(descriptor);
        }
    }

    protected JerseyTestWrapper jersey;
    protected ServletContext servletContext;

    @Before
    public void before() throws Exception {
        jersey = new JerseyTestWrapper(descriptor());
        jersey.setUp();

        servletContext = ContextCatchListener.servletContext;

        Optional<ServerConfig> serverConfig = ServerConfig.loadConfig("src/test/resources/config.json");
        ServerVariable.SERVER_CONFIG.set(servletContext, serverConfig);
    }

    @After
    public void after() throws Exception {
        jersey.tearDown();
    }

    public String packageName() {
        return ServerResource.class.getPackage().getName();
    }

    private WebAppDescriptor descriptor() {
        return new WebAppDescriptor.Builder(packageName())
                .contextListenerClass(ContextCatchListener.class)
                .contextListenerClass(ConfigListener.class)
                .contextListenerClass(FtlListener.class)
                .contextListenerClass(ResourceHandlerListener.class)
                .build();
    }

}
