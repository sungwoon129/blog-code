package com.blog.springrabbitmq;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageDto {

    private String title;
    private String body;



    @Builder
    public MessageDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
