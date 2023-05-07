package com.blog.spring_event_with_slack.spring_event_with_slack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringEventWithSlackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEventWithSlackApplication.class, args);
	}

}
