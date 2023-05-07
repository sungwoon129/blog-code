package com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure;

public class SlackAlarmFailedException extends RuntimeException{
    public SlackAlarmFailedException(String s) {
        super(s);
    }
}
