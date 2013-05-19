package org.hogel;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.hogel.JsonData;
import org.hogel.ServerVariable;
import org.hogel.handler.ResourceHandler;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("{path: .*}")
public class ServerResource {
    @Context
    ServletContext context;

    @Context
    Request request;

    @GET
    public Response process(@PathParam("path") String path) throws IOException, TemplateException {
        Optional<List<ResourceHandler>> handlers = ServerVariable.SERVER_RESOURCE_HANDLER.get(context);
        for (ResourceHandler handler : handlers.get()) {
            Optional<Response> response = handler.process(context, request, path);
            if (response.isPresent())
                return response.get();
        }
        return Response.status(HttpStatus.NOT_FOUND_404.getStatusCode()).build();
    }
}
