package com.training.jms.spring.demo.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${jms.test.queue}")
	private String queue;
	
	public void sendMessage(String message) {
		jmsTemplate.convertAndSend(queue, message);
		logger.info("Message sent from Producer...");
	}
	
	/**
	 * Setter method signifies that Destination is a Topic
	 * @param message
	 */
	public void sendMessageOnTopic(String message) {
		jmsTemplate.convertAndSend(queue, message);
		jmsTemplate.setPubSubDomain(true);
		logger.info("Message sent from Producer...");
	}

	/**
	 * This might be used if need to set some attributes on TextMessage.
	 * @param message
	 */
	public void sendMessageUsingMessageCreator(String message) {
		MessageCreator messageCreator = m -> m.createTextMessage(message) ;
		
		jmsTemplate.send(queue, messageCreator);
		logger.info("Message sent from Producer...");
	}
}
