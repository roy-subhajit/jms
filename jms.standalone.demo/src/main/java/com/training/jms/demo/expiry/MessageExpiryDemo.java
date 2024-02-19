package com.training.jms.demo.expiry;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageExpiryDemo {
	private static final Logger logger = LoggerFactory.getLogger(MessageExpiryDemo.class);
	
	public void createAndReceiveMessage() throws NamingException, JMSException, InterruptedException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create producer and set expiration time
			JMSProducer producer = jmsContext.createProducer();
			producer.setTimeToLive(2000);
			producer.send(queue, "Sending Hello from JMS 2.0 implementation");
			logger.info("Message sent successfuly by producer");
			
			//Wait more than time to live
			Thread.sleep(3000);
			
			//Receive Message object and check value
			Message messageReceived = jmsContext.createConsumer(queue).receive(4000);
			logger.info("Message received by consumer >>> {}", messageReceived);
		}
	}
	
	public void fetchFromExpiryQueue() throws NamingException, InterruptedException, JMSException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		Queue expiryQueue = (Queue)context.lookup("jms/expiry/queue");
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create producer and set expiration time
			JMSProducer producer = jmsContext.createProducer();
			producer.setTimeToLive(2000);
			producer.send(queue, "Sending Hello from JMS 2.0 implementation");
			logger.info("Message sent successfuly by producer");
			
			//Wait more than time to live
			Thread.sleep(3000);
			
			//Print response from test.Queue
			Message message = jmsContext.createConsumer(queue).receive(4000);
			logger.info("Message received by consumer from test.Queue >>> {}", message);
			
			//Receive Message object and check value from expiryQueue
			Message messageReceived = jmsContext.createConsumer(expiryQueue).receive(4000);
			logger.info("Message received by consumer from expiryQueue >>> {}", ((TextMessage)messageReceived).getText());
		}
	}
}
