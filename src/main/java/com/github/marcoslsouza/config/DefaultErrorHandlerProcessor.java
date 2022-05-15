package com.github.marcoslsouza.config;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DefaultErrorHandlerProcessor implements Processor {

	@Override
	public void process(Exchange exchange) {
	
		log.error("Error trying to send message. {}", exchange.getException().getMessage());
	}
}
