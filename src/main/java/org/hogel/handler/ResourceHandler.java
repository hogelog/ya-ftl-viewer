package org.hogel.handler;

import com.google.common.base.Optional;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public interface ResourceHandler {
    Optional<Response> process(ServletContext context, Request request, String path);

    void close();
}
