package org.hogel.resource;

import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.hogel.ServerResource;
import org.hogel.listener.FtlListener;
import org.hogel.listener.ResourceHandlerListener;
import org.junit.After;
import org.junit.Before;

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

    public String packageName() {
        return ServerResource.class.getPackage().getName();
    }

    private WebAppDescriptor descriptor() {
        return new WebAppDescriptor.Builder(packageName())
                .contextListenerClass(FtlListener.class)
                .contextListenerClass(ResourceHandlerListener.class)
                .build();
    }

}
