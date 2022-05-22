package com.github.marcoslsouza.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.direct.DirectComponent;
import org.apache.camel.component.direct.DirectEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.marcoslsouza.dto.EmployeeDTO;

@ExtendWith(MockitoExtension.class)
public class RabbitMQServiceProducerTest {

	@InjectMocks
	RabbitMQServiceProducer rabbitMQServiceProducer;
	
	@Mock
	private ProducerTemplate producerTemplate;
	
	@Test
	void createEmployeeWithoutReturningException() {
		
		when(producerTemplate.getDefaultEndpoint()).thenReturn(new DirectEndpoint("", new DirectComponent()));
		
		EmployeeDTO emp = EmployeeDTO.builder()
				.name("testeDev")
				.office("testeOffice")
				.build();
		
		when(this.producerTemplate.asyncSendBody(this.producerTemplate.getDefaultEndpoint(), emp)).thenReturn(CompletableFuture.completedFuture(emp));
		
		assertDoesNotThrow(() -> this.rabbitMQServiceProducer.createEmployee(emp));
	}
	
	@Test
	void createEmployeeWithExceptionReturn() {
		
		when(producerTemplate.getDefaultEndpoint()).thenReturn(new DirectEndpoint("", new DirectComponent()));
		
		EmployeeDTO emp = EmployeeDTO.builder()
				.name("testeDev")
				.office("testeOffice")
				.build();
		
		Throwable exception = new RuntimeException();
		when(this.producerTemplate.asyncSendBody(this.producerTemplate.getDefaultEndpoint(), emp)).thenReturn(CompletableFuture.failedFuture(exception));
		
		assertThrows(ExecutionException.class, () -> this.rabbitMQServiceProducer.createEmployee(emp));
	}
}
