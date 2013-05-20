package org.hogel.handler;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.hogel.resource.ResourceTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClassPathResourceHandlerTest extends ResourceTestCase {
    @Test
    public void livereload_js() {
        WebResource resource = jersey.resource();
        ClientResponse res = resource
                .path("/livereload.js")
                .accept("text/javascript")
                .get(ClientResponse.class);
        assertThat(res.getLength(), is(33592));
    }
}
