package com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure;

import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderPlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacedEventHandler {
    private AlarmService alarmService;

    public OrderPlacedEventHandler(AlarmService alarmService) {
        this.alarmService = alarmService;
    }


    @Async
    @EventListener(OrderPlacedEvent.class)
    public void handle(OrderPlacedEvent evt) {
        alarmService.sendAlarm(evt);

    }
}
