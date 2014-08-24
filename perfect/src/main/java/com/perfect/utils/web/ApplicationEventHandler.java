package com.perfect.utils.web;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
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
        System.out.println("event = " + event);
    }
}
