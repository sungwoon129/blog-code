package com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.Product;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderLine;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderPlacedEvent;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static java.util.stream.Collectors.joining;

@Service
public class SlackAlarmService implements AlarmService {

    @Value(value = "${slack.token}")
    private String token;
    @Value(value = "${slack.channel.monitor}")
    private String channel;
    @Override
    public void sendAlarm(OrderPlacedEvent evt) {
        try{
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*수취인 이름:*\n" + evt.getShippingInfo().getReceiver().getName()));
            textObjects.add(markdownText("*우편번호:*\n" + evt.getShippingInfo().getAddress().getZipCode()));
            textObjects.add(markdownText("*배송지:*\n" + evt.getShippingInfo().getAddress().getFullAddress()));
            textObjects.add(markdownText("*구매한 상품:*\n" + evt.getOrderLines().stream().map(orderLine -> orderLine.getProduct().getName()).collect(joining(","))));
            textObjects.add(markdownText("*구매 날짜:*\n" + evt.getOrderDate()));
            

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .blocks(asBlocks(
                            header(header -> header.text(plainText( evt.getShippingInfo().getReceiver().getName() + "님에게 상품을 배송해야합니다"))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            new SlackAlarmFailedException("슬랙알람 전송이 실패하였습니다.");
        }

    }
}
