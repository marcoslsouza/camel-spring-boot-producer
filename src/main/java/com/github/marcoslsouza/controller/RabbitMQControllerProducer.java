package com.github.marcoslsouza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.dto.EmployeeDTO;
import com.github.marcoslsouza.service.RabbitMQServiceProducer;

@RestController
@RequestMapping("/v1/producer")
public class RabbitMQControllerProducer {

	RabbitMQServiceProducer rabbitMQServiceProducer;

	@Autowired
	public RabbitMQControllerProducer(RabbitMQServiceProducer rabbitMQServiceProducer) {
		this.rabbitMQServiceProducer = rabbitMQServiceProducer;
	}
	
	@PostMapping("create")
	public String createEmployee(@RequestBody EmployeeDTO employee) {
		
		this.rabbitMQServiceProducer.createEmployee(employee);
		return "Employee data sent to queue";
	}
}
