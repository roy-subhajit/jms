package com.training.jms.demo.queue;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncEmailSenderApp implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(AsyncEmailSenderApp.class);
	
	public void runListener() throws NamingException, InterruptedException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {			

			//Create consumer and receive String message on the fly (i.e. without need to type caste to Message etc.)
			JMSConsumer jmsConsumer = jmsContext.createConsumer(queue);
			jmsConsumer.setMessageListener(new EmailSenderListener());
			logger.info("<< Message listener [STARTED] >>");
			Thread.sleep(10000);
			logger.info("<< Message listener [END] >>");
		}
	}

	@Override
	public void run() {
		try {
			runListener();
		} catch (NamingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
