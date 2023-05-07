package com.blog.spring_event_with_slack.spring_event_with_slack.common.event;


import org.springframework.context.ApplicationEventPublisher;

public class Events {
    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if(publisher != null) {
            publisher.publishEvent(event);
        }
    }

}
