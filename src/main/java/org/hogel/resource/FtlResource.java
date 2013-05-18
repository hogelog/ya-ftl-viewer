package org.hogel.resource;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.hogel.JsonData;
import org.hogel.ServerVariable;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Path("{path: .*}")
public class FtlResource {
    @Context ServletContext context;

    @GET
    @Produces("text/plain")
    public String ftlPath(@PathParam("path") String path) throws IOException, TemplateException {
        Configuration config = ServerVariable.FTL_CONFIG.get(context);
        String ftlName = path + ".ftl";
        String jsonName = path + ".json";
        Optional<JsonData> data = JsonData.load(jsonName);
        Template template = config.getTemplate(ftlName);
        return processTemplate(template, data);
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
}
