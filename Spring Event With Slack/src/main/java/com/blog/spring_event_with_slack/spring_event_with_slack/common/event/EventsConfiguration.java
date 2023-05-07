package com.blog.spring_event_with_slack.spring_event_with_slack.common.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(context);
    }

}
