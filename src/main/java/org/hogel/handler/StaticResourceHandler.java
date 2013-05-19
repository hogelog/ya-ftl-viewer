package org.hogel.handler;

import com.google.common.base.Optional;
import com.google.common.io.Files;
import org.hogel.ServerConfig;
import org.hogel.ServerVariable;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

public class StaticResourceHandler implements ResourceHandler {
    @Override
    public Optional<Response> process(ServletContext context, Request request, String path) {
        Optional<ServerConfig> serverConfig = ServerVariable.SERVER_CONFIG.get(context);
        File file;
        if (serverConfig.isPresent()) {
            file = new File(serverConfig.get().getBaseDir(), path);
        } else {
            file = new File(path);
        }

        if (!file.exists())
            return Optional.absent();
        try {
            byte[] data = Files.toByteArray(file);
            Response res = Response.ok(data).build();
            return Optional.of(res);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public void close() {
    }
}
