package com.github.marcoslsouza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employee) {
		
		try {
			this.rabbitMQServiceProducer.createEmployee(employee);
			return ResponseEntity.ok("Employee data sent to queue");
		} catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to deliver message");
		}
	}
}
