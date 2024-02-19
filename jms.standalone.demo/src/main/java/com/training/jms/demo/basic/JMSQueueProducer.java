package com.training.jms.demo.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSQueueProducer {
	private static final Logger logger = LoggerFactory.getLogger(JMSQueueProducer.class); 
	public void produceQueue() {
		
		try {
			// Create a new initial context, which loads from jndi.properties file
			Context context = new InitialContext();
			
			// Lookup an existing Destination which is a queue in our example
			Queue queue = (Queue)context.lookup("jms/test/queue"); 
			
			// Lookup the connection factory: 
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
			
			// Create a new Connection for sending messages to Queue 
			Connection connection = connectionFactory.createConnection(); 

			// Create a new session for the client
			Session session = connection.createSession();
			
			// Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(queue);
            
            // Create a messages
            TextMessage message = session.createTextMessage("Hello World, this is my first JMS message...");

            // Tell the producer to send the message
            producer.send(message);
            logger.info("Message sent...");
            
            //
            session.close();
            connection.close();
            logger.info("Connection closed.");
			 
			// Create a new subscriber to receive messages: 
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
}
