package com.training.jms.demo.setjmsreplyto;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSMyAppTwo implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JMSMyAppTwo.class);
	
	public void createAndReceiveMessage() throws NamingException, JMSException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue requestQueue = (Queue)context.lookup("jms/request/queue"); 
		//Queue replyQueue = (Queue)context.lookup("jms/reply/queue"); Not required as getting from getJMSReplyTo
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create consumer and receive message which has the reply destination set
			Message messageReceived = jmsContext.createConsumer(requestQueue).receive();
			logger.info("Message received by AppTwo >>> {}", ((TextMessage)messageReceived).getText());

			//Create producer and send message on the fly
			jmsContext.createProducer().send(messageReceived.getJMSReplyTo(), "[AppTwo] Hello Recevied...");
			logger.info("Reply message sent successfuly from AppTwo");
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			createAndReceiveMessage();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
