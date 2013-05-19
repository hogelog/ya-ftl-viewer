package org.hogel;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.hogel.resource.ResourceTestCase;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ServerResourceTest extends ResourceTestCase {
    @Override
    public String packageName() {
        return ServerResource.class.getPackage().getName();
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

    @Test
    public void notFound() {
        WebResource resource = jersey.resource();
        try {
            resource.path("/hoge").get(Response.class);
            assertTrue(false);
        } catch (UniformInterfaceException e) {
            ClientResponse res = e.getResponse();
            assertThat(res.getStatus(), is(HttpStatus.NOT_FOUND_404.getStatusCode()));
        }
    }
}
