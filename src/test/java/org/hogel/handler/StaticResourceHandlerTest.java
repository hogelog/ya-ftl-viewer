package org.hogel.handler;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.hogel.resource.ResourceTestCase;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StaticResourceHandlerTest extends ResourceTestCase {
    @Test
    public void png() {
        WebResource resource = jersey.resource();
        ClientResponse res = resource
                .path("/test.png")
                .accept("image/png")
                .get(ClientResponse.class);
        assertThat(res.getLength(), is(13135));
    }
}
