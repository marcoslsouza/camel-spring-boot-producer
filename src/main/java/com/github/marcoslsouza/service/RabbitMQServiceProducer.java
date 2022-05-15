package com.github.marcoslsouza.service;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import com.github.marcoslsouza.dto.EmployeeDTO;

@Service
public class RabbitMQServiceProducer {
	
	@Produce(value = "direct:startQueueEmployee")
	private ProducerTemplate template;
	
	public void createEmployee(EmployeeDTO emp) {
		
		this.template.asyncSendBody(this.template.getDefaultEndpoint(), emp);
	}
}
