package org.hogel;

import com.google.common.base.Optional;

import javax.servlet.ServletContext;

public enum ServerVariable {
    FTL_CONFIG,
    SERVER_RESOURCE_HANDLER,
    SERVER_CONFIG,
    ;

    public <T> void set(ServletContext context, Optional<T> value) {
        context.setAttribute(name(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(ServletContext context) {
        return (Optional<T>) context.getAttribute(name());
    }
}
