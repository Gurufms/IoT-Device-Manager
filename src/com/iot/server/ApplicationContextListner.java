package com.iot.server;

import org.apache.log4j.Logger; 
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationContextListner implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = Logger.getLogger(ApplicationContextListner.class); 
    public static ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();
        logger.info("Application context refreshed: " + applicationContext.getDisplayName()); 
    }
}

