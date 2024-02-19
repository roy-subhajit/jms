package com.training.jms.demo.basic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSTopicProducer {
	private static final Logger logger = LoggerFactory.getLogger(JMSTopicProducer.class);
	
	public void produceTopic() {
		
		try {
			//It is important to understand that the consumer will have to listen to the Topic first.
			//Any incoming messages to the Topic will only then be received by the consumers.
			//Consumers registered after message delivery will not pickup existing messages.
			Thread.sleep(5000);
			
			// Create a new initial context, which loads from jndi.properties file
			Context context = new InitialContext();

			// Lookup an existing Destination which is a topic in our example
			Topic topic = (Topic)context.lookup("jms/test/topic"); 
			
			// Lookup the connection factory: 
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
			
			// Create a new Connection for sending messages to Queue 
			Connection connection = connectionFactory.createConnection(); 

			// Create a new session for the client
			Session session = connection.createSession();
			
			// Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(topic);
            
            // Create a messages
            TextMessage message = session.createTextMessage("Hello World, I'm a message published in a Topic !");

            // Tell the producer to send the message
            producer.send(message);
            logger.info("Message sent from Producer...");
            
            // Close the connections
            session.close();
            connection.close();
            logger.info("Connection closed.");
            
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
}
