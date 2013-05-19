package org.hogel;

import com.google.common.base.Optional;

import javax.servlet.ServletContext;

public enum ServerVariable {
    FTL_CONFIG,
    SERVER_RESOURCE_HANDLER,
    ;

    public void set(ServletContext context, Object value) {
        context.setAttribute(name(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ServletContext context) {
        return (T) context.getAttribute(name());
    }
}
