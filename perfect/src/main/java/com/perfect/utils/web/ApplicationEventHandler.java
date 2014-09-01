package com.perfect.utils.web;

import com.perfect.core.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Created by yousheng on 2014/8/23.
 *
 * @author yousheng
 */
@Component
public class ApplicationEventHandler implements ApplicationListener {
    private Logger logger = LoggerFactory.getLogger(ApplicationEventHandler.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug(event.toString());
        }
        if (event instanceof InteractiveAuthenticationSuccessEvent) {
            InteractiveAuthenticationSuccessEvent interactiveAuthenticationSuccessEvent = (InteractiveAuthenticationSuccessEvent) event;
            String userName = interactiveAuthenticationSuccessEvent.getAuthentication().getName();
            AppContext.setUser(userName);
        } else if (event instanceof AfterSaveEvent) {
            System.out.println("event = " + event);
        }
    }
}
