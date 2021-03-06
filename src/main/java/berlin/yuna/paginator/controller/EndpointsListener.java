package berlin.yuna.paginator.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Log LOG = LogFactory.getLog(EndpointsListener.class);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext applicationContext = event.getApplicationContext();
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((requestMappingInfo, handlerMethod) -> {
                    if (!requestMappingInfo.getMethodsCondition().getMethods().isEmpty()) {
                        for(String pattern : requestMappingInfo.getPatternValues()){
                            LOG.info("Started endpoint: " + requestMappingInfo.getMethodsCondition().getMethods() + " " + pattern);
                        }
                    }
                });
    }
}