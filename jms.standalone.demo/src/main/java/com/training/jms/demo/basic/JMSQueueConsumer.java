package com.training.jms.demo.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSQueueConsumer {
	private static final Logger logger = LoggerFactory.getLogger(JMSQueueConsumer.class);
	
	public void consumeQueue() {
		try {
			// Create a new initial context, which loads from jndi.properties file
			Context context = new InitialContext();
			
			// Lookup the connection factory: 
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
			
			// Create a new Connection for sending messages to Queue 
			Connection connection = connectionFactory.createConnection(); 

			// Create a new session for the client
			Session session = connection.createSession();
			
			// Lookup an existing Destination which is a queue in our example
			Queue queue = (Queue)context.lookup("jms/test/queue"); 
			
			//Create consumer
			MessageConsumer consumer = session.createConsumer(queue);
			
			//
			connection.start();
			TextMessage textMessage = (TextMessage) consumer.receive(5000);
			logger.info("Message received from queue >>> {}", textMessage.getText());
			
			//
            session.close();
            connection.close();
            logger.info("Connection closed.");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
}
