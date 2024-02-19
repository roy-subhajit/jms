package com.training.jms.spring.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.training.jms.spring.demo.sender.Producer;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private Producer producer;

	@Test
	void sendMessage() {
		producer.sendMessage("Hello from Spring JMS !!!");
	}

}
