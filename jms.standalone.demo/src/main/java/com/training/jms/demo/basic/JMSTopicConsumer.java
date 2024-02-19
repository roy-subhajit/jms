package com.training.jms.demo.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSTopicConsumer implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JMSTopicConsumer.class);
	public void consumeTopic() {
		try {
			// Create a new initial context, which loads from jndi.properties file
			Context context = new InitialContext();
			
			// Lookup an existing Destination which is a queue in our example
			Topic topic = (Topic)context.lookup("jms/test/topic"); 
			
			// Lookup the connection factory: 
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
			
			// Create a new Connection for sending messages to Queue 
			Connection connection = connectionFactory.createConnection(); 

			// Create a new session for the client
			Session session = connection.createSession();
			
			//Here create multiple consumers to demonstrate publish/subscribe model
			MessageConsumer consumerOne = session.createConsumer(topic);
			MessageConsumer consumerTwo = session.createConsumer(topic);
			
			// Print all messages received by subscribers
			connection.start();
			logger.info("Message receivers are active now...");
			logger.info("Message received by consumerOne >>> {}", ((TextMessage) consumerOne.receive(10000)).getText());
			logger.info("Message received by consumerTwo >>> {}", ((TextMessage) consumerTwo.receive(10000)).getText());
			
			// Close connection
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
	@Override
	public void run() {
		consumeTopic();
	}
}
