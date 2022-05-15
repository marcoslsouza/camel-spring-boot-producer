package com.github.marcoslsouza.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.context.annotation.Configuration;

import com.github.marcoslsouza.config.DefaultErrorHandlerProcessor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitMQRouteProducer extends RouteBuilder {

	private final DefaultErrorHandlerProcessor defaultErrorHandlerProcessor;
	
	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat();
		
		errorHandler(defaultErrorHandler()
				.log("Error in RabbitMQRouteProducer")
				.onExceptionOccurred(defaultErrorHandlerProcessor)
		);
		
		from("direct:startQueueEmployee")
			.id("idOfQueueHere")
			.marshal(jsonDataFormat)
			.log("Message sent. Body: ${body}")
			.to("{{rabbitmq.camel.producer.uri}}").end();
	}
}
