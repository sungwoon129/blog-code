package com.blog.spring_event_with_slack.spring_event_with_slack.order.domain;

import com.blog.spring_event_with_slack.spring_event_with_slack.member.MemberId;

public interface OrdererService {
    Orderer createOrderer(MemberId ordererMemberId);
}
