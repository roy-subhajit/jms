package com.training.jms.demo.basic;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMS2MessagePriority {
	private static final Logger logger = LoggerFactory.getLogger(JMS2MessagePriority.class);
	
	public void sendReceiveMessage() throws NamingException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Get the producer
			JMSProducer producer = jmsContext.createProducer();
			
			producer.setPriority(2);
			producer.send(queue, "Message ONE");
			logger.info("Message ONE sent...");
			
			producer.setPriority(1);
			producer.send(queue, "Message TWO");
			logger.info("Message TWO sent...");
			
			producer.setPriority(9);
			producer.send(queue, "Message THREE");
			logger.info("Message THREE sent...");
			
			//Create the consumer
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			for(int i = 0; i < 3; i++) {
				logger.info("Received message from Queue >>> {}", consumer.receiveBody(String.class, 1000));
			}
		}
	}
			
}
