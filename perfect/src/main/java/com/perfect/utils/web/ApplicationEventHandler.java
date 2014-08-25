package com.perfect.utils.web;

import com.perfect.core.AppContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Created by yousheng on 2014/8/23.
 *
 * @author yousheng
 */
@Component
public class ApplicationEventHandler implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof InteractiveAuthenticationSuccessEvent) {
            InteractiveAuthenticationSuccessEvent interactiveAuthenticationSuccessEvent = (InteractiveAuthenticationSuccessEvent) event;


            String userName = interactiveAuthenticationSuccessEvent.getAuthentication().getName();
            AppContext.setUser(userName);
        }
    }
}
