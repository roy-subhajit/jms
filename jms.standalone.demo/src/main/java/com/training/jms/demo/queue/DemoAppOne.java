package com.training.jms.demo.queue;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.jms.demo.basic.JMS2ContextDemo;
import com.training.jms.dto.Mail;

public class DemoAppOne {
	private static final Logger logger = LoggerFactory.getLogger(JMS2ContextDemo.class);
	
	public void sendMessageToQueue() throws NamingException, InterruptedException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {

			//Create mail content and send to listener to receive messages in async fashion
			for(int i = 1; i < 4; i++) {
				Mail mail = new Mail();
				mail.setSender("tony@gmail.com");
				mail.setSubject("Order Update");
				mail.setBody("Your order is closd now...");
				jmsContext.createProducer().send(queue, mail);
				logger.info("Message sent successfuly by producer");
				Thread.sleep(i*1000);
			}

		}
	}
}
