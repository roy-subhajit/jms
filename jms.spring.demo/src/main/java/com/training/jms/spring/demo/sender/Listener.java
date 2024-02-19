package com.training.jms.spring.demo.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
	private static final Logger logger = LoggerFactory.getLogger(Listener.class);
	
	@JmsListener(destination = "${jms.test.queue}")
	public void receive(String message) {
		logger.info("Message received >>> {}", message);
	}
}
