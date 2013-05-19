package org.hogel.handler;

import com.sun.jersey.api.client.WebResource;
import org.hogel.resource.ResourceTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FtlResourceHandlerTest extends ResourceTestCase {
    @Test
    public void index() {
        WebResource resource = jersey.resource();
        String res = resource.path("/index").get(String.class);
        assertThat(res, is("Hello!\n"));
    }

    @Test
    public void hoge() {
        WebResource resource = jersey.resource();
        String res = resource.path("/hoge").get(String.class);
        assertThat(res, is("Hello hogelog!\n"));
    }
}
