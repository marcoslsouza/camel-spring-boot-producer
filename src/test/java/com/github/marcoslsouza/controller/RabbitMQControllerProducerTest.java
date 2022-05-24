package com.github.marcoslsouza.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marcoslsouza.dto.EmployeeDTO;
import com.github.marcoslsouza.service.RabbitMQServiceProducer;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(RabbitMQControllerProducer.class)
public class RabbitMQControllerProducerTest {
	
	static String EMPLOYEE_API = "/v1/producer/create";
	
	@MockBean
	RabbitMQServiceProducer rabbitMQServiceProducer;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void postCreateEmployeeReturn200() throws Exception {
		
		EmployeeDTO emp = EmployeeDTO.builder()
				.name("testeDev")
				.office("testeOffice")
				.build();
		
		String json = new ObjectMapper().writeValueAsString(emp);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.post(EMPLOYEE_API)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(json);
		
		mockMvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("Employee data sent to queue"));
	}
	
	@Test
	void postCreateEmployeeReturn500() throws Exception {
		
		EmployeeDTO emp = EmployeeDTO.builder()
				.name("testeDev")
				.office("testeOffice")
				.build();
		
		String json = new ObjectMapper().writeValueAsString(emp);
		
		Throwable exception = new RuntimeException();
		when(this.rabbitMQServiceProducer.createEmployee(emp)).thenThrow(exception);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.post(EMPLOYEE_API)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(json);
		
		mockMvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isInternalServerError())
			.andExpect(MockMvcResultMatchers.content().string("Failed to deliver message"));
	}
}
