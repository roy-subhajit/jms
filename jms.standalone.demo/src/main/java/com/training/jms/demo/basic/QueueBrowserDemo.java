package com.training.jms.demo.basic;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueBrowserDemo {
	private static final Logger logger = LoggerFactory.getLogger(QueueBrowserDemo.class);
	
	public void browse() {
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
            
            //<NEW> Create the browser here
            QueueBrowser queueBrowser = session.createBrowser(queue);
            
            //Browse through available messages in the Queue
            Enumeration<?> textMessages = queueBrowser.getEnumeration();
            while(textMessages.hasMoreElements()) {
            	TextMessage textMessage = (TextMessage) textMessages.nextElement();
            	logger.info("Browser showing message >>> {}", textMessage.getText());
            }
            
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
