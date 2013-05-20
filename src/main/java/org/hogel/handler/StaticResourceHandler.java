package org.hogel.handler;

import com.google.common.base.Optional;
import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
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
            file = new File(serverConfig.get().getStaticDir(), path);
        } else {
            file = new File(path);
        }

        String mediaType = getMediaType(path);

        if (!file.exists() || file.isDirectory())
            return Optional.absent();
        try {
            byte[] data = Files.toByteArray(file);
            Response res = Response
                    .ok(data)
                    .type(mediaType)
                    .build();
            return Optional.of(res);
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
    }

    private static class Type {
        public String mediaType;
        public String[] extensions;
        public Type(String mediaType, String... extensions) {
            this.mediaType = mediaType;
            this.extensions = extensions;
        }
    }

    public static Type[] MEDIA_TYPES = new Type[]{
            new Type("text/html", "html", "htm"),
            new Type("text/plain", "txt"),
            new Type("text/javascript", "js"),
            new Type("text/css", "css"),
            new Type("text/xml", "xml"),
            new Type("application/json", "json"),
            new Type("application/zip", "zip"),
            new Type("application/pdf", "json"),
            new Type("image/jpeg", "jpg", "jpeg"),
            new Type("image/png", "png"),
            new Type("image/gif", "gif"),
            new Type("image/bmp", "bmp"),
    };

    public static String getMediaType(String path) {
        String extension = FilenameUtils.getExtension(path);
        for (Type type :MEDIA_TYPES) {
            for (String typeExtension : type.extensions) {
                if (typeExtension.equals(extension)) {
                    return type.mediaType;
                }
            }
        }
        return "text/html";
    }

    @Override
    public void close() {
    }
}
