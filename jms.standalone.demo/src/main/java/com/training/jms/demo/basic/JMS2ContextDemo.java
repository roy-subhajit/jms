package com.training.jms.demo.basic;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMS2ContextDemo {
	private static final Logger logger = LoggerFactory.getLogger(JMS2ContextDemo.class);
	
	public void createAndReceiveMessage() throws NamingException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create producer and send message on the fly
			jmsContext.createProducer().send(queue, "Sending Hello from JMS 2.0 implementation");
			logger.info("Message sent successfuly by producer");
			
			//Create consumer and receive String message on the fly (i.e. without need to type caste to Message etc.)
			String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
			logger.info("Message received by consumer >>> {}", messageReceived);
		}
	}
}
