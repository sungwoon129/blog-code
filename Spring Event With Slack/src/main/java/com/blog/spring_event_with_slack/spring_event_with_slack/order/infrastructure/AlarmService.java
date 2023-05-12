package com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure;

import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderPlacedEvent;

public interface AlarmService {

    void sendAlarm(OrderPlacedEvent evt);
}
