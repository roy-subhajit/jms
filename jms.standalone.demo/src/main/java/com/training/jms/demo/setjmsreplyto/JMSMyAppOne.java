package com.training.jms.demo.setjmsreplyto;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSMyAppOne implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JMSMyAppOne.class);
	
	public void createAndReceiveMessage() throws NamingException, JMSException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue requestQueue = (Queue)context.lookup("jms/request/queue"); 
		//Queue replyQueue = (Queue)context.lookup("jms/reply/queue"); //Creating programmatically in next step
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Can create programmatically as well.
			Queue replyQueue = jmsContext.createTemporaryQueue();
			
			//Create producer and send message with the reply destination
			TextMessage message = jmsContext.createTextMessage("[AppOne] Hello...");
			message.setJMSReplyTo(replyQueue);
			jmsContext.createProducer().send(requestQueue, message);
			logger.info("Message sent successfuly from AppOne");
			
			//Create consumer and receive String message on the fly (i.e. without need to type caste to Message etc.)
			String messageReceived = jmsContext.createConsumer(replyQueue).receiveBody(String.class);
			logger.info("Reply message received by AppOne >>> {}", messageReceived);
		}
	}

	@Override
	public void run() {
		try {
			createAndReceiveMessage();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
