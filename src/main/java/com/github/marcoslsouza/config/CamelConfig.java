package com.github.marcoslsouza.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class CamelConfig {
	
	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		
	    ConnectionFactory connectionFactory = new ConnectionFactory();
	    connectionFactory.setHost("{camel.component.rabbitmq.host-name}");
	    connectionFactory.setPort(Integer.parseInt("{camel.component.rabbitmq.port-number}"));
	    connectionFactory.setUsername("{camel.component.rabbitmq.username}");
	    connectionFactory.setPassword("{camel.component.rabbitmq.password}");

	    return connectionFactory;
	}
}
