package com.github.marcoslsouza.route;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RabbitMQRouteProducerTest {

	@InjectMocks
	RabbitMQRouteProducer rabbitMQRouteProducer;
	
	@Test
	void whenConfigureExpectedNoException() {
		assertDoesNotThrow(() -> this.rabbitMQRouteProducer.configure());
	}
}
