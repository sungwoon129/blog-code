package com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure;

import com.blog.spring_event_with_slack.spring_event_with_slack.member.MemberId;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.Orderer;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrdererService;
import org.springframework.stereotype.Service;

@Service
public class OrdererServiceImpl implements OrdererService {
    @Override
    public Orderer createOrderer(MemberId ordererMemberId) {
        return new Orderer(MemberId.of(ordererMemberId.getId()),"스프링 이벤트 테스트");
    }
}
