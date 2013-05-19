package org.hogel.handler;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.hogel.JsonData;
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
        Configuration config = ServerVariable.FTL_CONFIG.get(context);
        String ftlName = path + ".ftl";
        String jsonName = path + ".json";
        File file = new File(ftlName);
        if (!file.exists()) {
            return Optional.absent();
        }
        try {
            Optional<JsonData> data = JsonData.load(jsonName);
            Template template = config.getTemplate(ftlName);
            String html = processTemplate(template, data);
            return Optional.of(Response.ok(html).build());
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
