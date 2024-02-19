package com.training.jms.demo.basic;

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

public class JMS2PropertyDemo {
	private static final Logger logger = LoggerFactory.getLogger(JMS2PropertyDemo.class);
	
	public void createAndReceiveMessage() throws NamingException, JMSException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create TextMessage and set custom properties
			TextMessage textMessage = jmsContext.createTextMessage("Sending Hello from JMS 2.0 implementation");
			textMessage.setBooleanProperty("isOk", true);
			textMessage.setStringProperty("Greetings", "Hello there");
			
			//Create producer and send message
			jmsContext.createProducer().send(queue, textMessage);
			logger.info("Message sent successfuly by producer");
			
			//Create consumer and receive String message on the fly (i.e. without need to type caste to Message etc.)
			TextMessage messageReceived = (TextMessage)jmsContext.createConsumer(queue).receive(2000);
			logger.info("Message received by consumer >>> {}", messageReceived.getText());
			logger.info("Boolean property value received >>> {}", messageReceived.getBooleanProperty("isOk"));
			logger.info("String property value received >>> {}", messageReceived.getStringProperty("Greetings"));
		}
	}
}
