package com.github.marcoslsouza.service;

import java.util.concurrent.CompletableFuture;

import com.github.marcoslsouza.dto.EmployeeDTO;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceProducer {
	
	@Produce(value = "direct:startQueueEmployee")
	private ProducerTemplate producerTemplate;
	
	public Object createEmployee(EmployeeDTO emp) throws Throwable {
		
		CompletableFuture async = this.producerTemplate.asyncSendBody(this.producerTemplate.getDefaultEndpoint(), emp);
		return async.get();
	}
}
