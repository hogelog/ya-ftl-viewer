package org.hogel;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.hogel.resource.ResourceTestCase;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ServerResourceTest extends ResourceTestCase {

    @Test
    public void notFound() {
        WebResource resource = jersey.resource();
        try {
            resource.path("/fugafuga").get(Response.class);
            assertTrue(false);
        } catch (UniformInterfaceException e) {
            ClientResponse res = e.getResponse();
            assertThat(res.getStatus(), is(HttpStatus.NOT_FOUND_404.getStatusCode()));
        }
    }
}
