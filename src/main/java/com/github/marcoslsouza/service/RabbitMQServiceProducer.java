package com.github.marcoslsouza.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import com.github.marcoslsouza.dto.EmployeeDTO;

@Service
public class RabbitMQServiceProducer {
	
	@Produce(value = "direct:startQueueEmployee")
	private ProducerTemplate producerTemplate;
	
	public Object createEmployee(EmployeeDTO emp) throws InterruptedException, ExecutionException {
		
		CompletableFuture<Object> async = this.producerTemplate.asyncSendBody(this.producerTemplate.getDefaultEndpoint(), emp);
		return async.get();
	}
}
