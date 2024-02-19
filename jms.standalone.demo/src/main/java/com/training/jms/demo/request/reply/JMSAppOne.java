package com.training.jms.demo.request.reply;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSAppOne implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JMSAppOne.class);
	
	public void createAndReceiveMessage() throws NamingException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue requestQueue = (Queue)context.lookup("jms/request/queue"); 
		Queue replyQueue = (Queue)context.lookup("jms/reply/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create producer and send message on the fly
			jmsContext.createProducer().send(requestQueue, "[AppOne] Hello...");
			logger.info("Message sent successfuly from AppOne");
			
			//Create consumer and receive String message on the fly (i.e. without need to type caste to Message etc.)
			String messageReceived = jmsContext.createConsumer(replyQueue).receiveBody(String.class);
			logger.info("Message received by AppOne from replyQueue >>> {}", messageReceived);
		}
	}

	@Override
	public void run() {
		try {
			createAndReceiveMessage();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
