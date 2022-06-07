package com.github.marcoslsouza.controller;

import com.github.marcoslsouza.dto.EmployeeDTO;
import com.github.marcoslsouza.exception.FailedToDeliverMessage;
import com.github.marcoslsouza.service.RabbitMQServiceProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/producer")
public class RabbitMQControllerProducer {

	private final static String INTERNAL_SERVER_ERROR_CODE = "500";
	private final static String FAILED_DELIVER_MESSAGE = "Failed to deliver message";
	
	private RabbitMQServiceProducer rabbitMQServiceProducer;
	
	@Autowired
	public RabbitMQControllerProducer(RabbitMQServiceProducer rabbitMQServiceProducer) {
		this.rabbitMQServiceProducer = rabbitMQServiceProducer;
	}
	
	@PostMapping("create")
	public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employee) {
		
		try {
			this.rabbitMQServiceProducer.createEmployee(employee);
			return ResponseEntity.ok("Employee data sent to queue");
		} catch (Throwable e) {
			
			throw new FailedToDeliverMessage(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_CODE, FAILED_DELIVER_MESSAGE, 
					e.getMessage());
		}
	}
}
