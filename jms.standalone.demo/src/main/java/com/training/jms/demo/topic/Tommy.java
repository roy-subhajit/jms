package com.training.jms.demo.topic;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tommy implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(Tommy.class);
	
	public void receiveMessage() throws NamingException, InterruptedException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a topic in our example
		Topic topic = (Topic)context.lookup("jms/test/topic"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create consumer and receive String message on the fly (i.e. without need to type caste to Message etc.)
			String messageReceived = jmsContext.createConsumer(topic).receiveBody(String.class);
			logger.info("Message received by Tommy >>> {}", messageReceived);
		}
	}

	@Override
	public void run() {
		try {
			receiveMessage();
		} catch (NamingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
