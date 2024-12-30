package pl.wmsdev.utils.config;

import org.slf4j.MDC;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;
import java.util.Map;

public class SpringContextAwareTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable task) {
        var localeContext = LocaleContextHolder.getLocale();
        var contextMap = MDC.getCopyOfContextMap();
        var attributes = RequestContextHolder.currentRequestAttributes();
        var securityContext = SecurityContextHolder.getContext();

        return () -> {
            try {
                setContext(localeContext, contextMap, attributes, securityContext);
                task.run();
            } finally {
                clearContext();
            }
        };
    }

    private void setContext(Locale localeContext, Map<String, String> contextMap,
                                RequestAttributes attributes, SecurityContext securityContext) {
        LocaleContextHolder.setLocale(localeContext);
        MDC.setContextMap(contextMap);
        RequestContextHolder.setRequestAttributes(attributes);
        SecurityContextHolder.setContext(securityContext);
    }

    private void clearContext() {
        LocaleContextHolder.resetLocaleContext();
        MDC.clear();
        RequestContextHolder.resetRequestAttributes();
        SecurityContextHolder.clearContext();
    }
}
