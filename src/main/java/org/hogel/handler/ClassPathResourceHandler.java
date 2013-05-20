package org.hogel.handler;

import com.google.common.base.Optional;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResourceHandler implements ResourceHandler {
    @Override
    public Optional<Response> process(ServletContext context, Request request, String path) {
        InputStream resourceStream = getClass().getResourceAsStream("/" + path);
        if (resourceStream == null)
            return Optional.absent();

        try {
            byte[] data = IOUtils.toByteArray(resourceStream);
            String mediaType = StaticResourceHandler.getMediaType(path);
            Response res = Response
                    .ok(data)
                    .type(mediaType)
                    .build();
            return Optional.of(res);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        } finally {
            IOUtils.closeQuietly(resourceStream);
        }
    }

    @Override
    public void close() {
    }
}
