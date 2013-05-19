package org.hogel.handler;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.hogel.JsonData;
import org.hogel.ServerConfig;
import org.hogel.ServerVariable;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FtlResourceHandler implements ResourceHandler {
    @Override
    public Optional<Response> process(ServletContext context, Request request, String path) {
        Optional<Configuration> optionalConfig = ServerVariable.FTL_CONFIG.get(context);

        // freemarker config is not present
        if (!optionalConfig.isPresent())
            return Optional.absent();

        Configuration config = optionalConfig.get();

        Optional<ServerConfig> serverConfig = ServerVariable.SERVER_CONFIG.get(context);
        File ftlFile;
        File jsonFile;
        if (serverConfig.isPresent()) {
            // server confif file is present
            ftlFile = new File(serverConfig.get().getBaseDir(), path + ".ftl");
            jsonFile = new File(serverConfig.get().getBaseDir(), path + ".json");
        } else {
            // server confif file is not present
            ftlFile = new File(path + ".ftl");
            jsonFile = new File(path + ".json");
        }
        if (!ftlFile.exists()) {
            return Optional.absent();
        }
        try {
            Optional<JsonData> data = JsonData.load(jsonFile);
            Template template = config.getTemplate(ftlFile.getPath());
            String html = processTemplate(template, data);
            return Optional.of(Response.ok(html).type("text/html").build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String processTemplate(Template template, Optional<JsonData> data) throws IOException, TemplateException {
        Map<String, Object> map;
        if (data.isPresent()) {
            map = data.get().getData();
        } else {
            map = ImmutableMap.of();
        }
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        return writer.toString();
    }

    @Override
    public void close() {
    }
}
