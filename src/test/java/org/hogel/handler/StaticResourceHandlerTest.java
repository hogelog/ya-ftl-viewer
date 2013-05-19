package org.hogel.handler;

import com.sun.jersey.api.client.WebResource;
import org.hogel.resource.ResourceTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StaticResourceHandlerTest extends ResourceTestCase {
    @Test
    public void test() {
        WebResource resource = jersey.resource();
        String res = resource.path("/src/test/resources/web/index.ftl").get(String.class);
        assertThat(res, is("Hello<#if name?has_content>${name?html }</#if>!\n"));
    }
}
