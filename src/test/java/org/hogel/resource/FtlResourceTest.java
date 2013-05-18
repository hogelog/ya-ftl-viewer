package org.hogel.resource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.hogel.listener.FtlListener;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FtlResourceTest extends ResourceTestCase {
    @Override
    public String packageName() {
        return FtlResource.class.getPackage().getName();
    }

    @Test
    public void index() {
        WebResource resource = jersey.resource();
        String res = resource.path("/src/test/resources/web/index").get(String.class);
        System.out.println(res);
        assertThat(res, is("Hello!\n"));
    }

    @Test
    public void hoge() {
        WebResource resource = jersey.resource();
        String res = resource.path("/src/test/resources/web/hoge").get(String.class);
        System.out.println(res);
        assertThat(res, is("Hello hogelog!\n"));
    }
}
