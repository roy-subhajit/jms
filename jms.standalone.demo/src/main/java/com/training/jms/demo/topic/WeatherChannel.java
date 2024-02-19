package com.training.jms.demo.topic;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherChannel {
	private static final Logger logger = LoggerFactory.getLogger(WeatherChannel.class);
	
	public void broadcastMessage() throws NamingException, JMSException, InterruptedException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a topic in our example
		Topic topic = (Topic)context.lookup("jms/test/topic");
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Delay so that all consumers are fully active
			Thread.sleep(1000);
			
			//Create producer and send message on the fly
			jmsContext.createProducer().send(topic, "Today's weather at Kolkata is pleasant with max temp 27 C");
			logger.info("Message sent successfuly by producer");
			
		}
	}
}
