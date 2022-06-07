package com.github.marcoslsouza.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQRouteProducer extends RouteBuilder {

	@Override
	public void configure() {
		
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat();
		
		onException(RuntimeCamelException.class)
		    .useOriginalMessage()
		    .redeliveryDelay(1000)
		    .backOffMultiplier(1.5)
		    .maximumRedeliveries(3)
		    .retryAttemptedLogLevel(LoggingLevel.WARN)
		    .useExponentialBackOff()
		    .marshal(jsonDataFormat)
		    .to("{{rabbitmq.camel.producer.uri.dead}}");
		
		from("direct:startQueueEmployee")
			.id("idOfQueueHere")
			.marshal(jsonDataFormat)
			.log("Message sent. Body: ${body}")
			.to("{{rabbitmq.camel.producer.uri.main}}")
		    .end();
	}
}
