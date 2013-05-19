package org.hogel.resource;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.ResourceContext;
import freemarker.template.TemplateException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.hogel.ServerVariable;
import org.hogel.handler.ResourceHandler;

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
    @Context
    ServletContext servletContext;

    @Context
    Request request;

    @GET
    public Response process(@PathParam("path") String path) throws IOException, TemplateException {
        Optional<List<ResourceHandler>> handlers = ServerVariable.SERVER_RESOURCE_HANDLER.get(servletContext);
        for (ResourceHandler handler : handlers.get()) {
            Optional<Response> response = handler.process(servletContext, request, path);
            if (response.isPresent())
                return response.get();
        }
        return Response.status(HttpStatus.NOT_FOUND_404.getStatusCode()).build();
    }
}
