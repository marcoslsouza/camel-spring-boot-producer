package com.github.marcoslsouza.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQRouteProducer extends RouteBuilder {

	@Override
	public void configure() {
		
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat();
		
		from("direct:startQueueEmployee")
			.id("idOfQueueHere")
			.marshal(jsonDataFormat)
			.log("Message sent. Body: ${body}")
			.to("{{rabbitmq.camel.producer.uri}}")
			.end();
	}
}
