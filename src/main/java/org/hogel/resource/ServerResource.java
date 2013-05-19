package org.hogel.resource;

import com.google.common.base.Optional;
import freemarker.template.TemplateException;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.hogel.ServerVariable;
import org.hogel.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("{path: .*}")
public class ServerResource {
    private static final Logger LOG = LoggerFactory.getLogger(ServerResource.class);

    @Context
    ServletContext servletContext;

    @Context
    Request request;

    @GET
    public Response process(@PathParam("path") String path) throws IOException, TemplateException {
        LOG.info("access: {}", path);

        Optional<List<ResourceHandler>> handlers = ServerVariable.SERVER_RESOURCE_HANDLER.get(servletContext);
        for (ResourceHandler handler : handlers.get()) {
            Optional<Response> response = handler.process(servletContext, request, path);
            if (response.isPresent())
                return response.get();
        }
        return Response.status(HttpStatus.NOT_FOUND_404.getStatusCode()).build();
    }
}
