package org.hogel.resource;

import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.hogel.listener.FtlListener;
import org.junit.After;
import org.junit.Before;

import java.util.logging.Level;
import java.util.logging.LogManager;

public abstract class ResourceTestCase {
    private static class JerseyTestWrapper extends JerseyTest {
        public JerseyTestWrapper(WebAppDescriptor descriptor) {
            super(descriptor);
        }
    }

    protected JerseyTest jersey;

    @Before
    public void before() throws Exception {
        jersey = new JerseyTestWrapper(descriptor());
        jersey.setUp();
    }

    @After
    public void after() throws Exception {
        jersey.tearDown();
    }

    protected abstract String packageName();

    private WebAppDescriptor descriptor() {
        return new WebAppDescriptor.Builder(packageName())
//                .initParam("com.sun.jersey.spi.container.ContainerRequestFilters", "com.sun.jersey.api.container.filter.LoggingFilter")
                .contextListenerClass(FtlListener.class)
                .build();
    }

}
