package com.training.jms.demo.filter;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.jms.dto.Student;

public class MessageFilterApp {
private static final Logger logger = LoggerFactory.getLogger(MessageFilterApp.class);
	
	public void createAndReceiveMessage() throws NamingException, JMSException {
		// Create a new initial context, which loads from jndi.properties file
		Context context = new InitialContext();
		
		// Lookup an existing Destination which is a queue in our example
		Queue queue = (Queue)context.lookup("jms/test/queue"); 
		
		//Object in a try-with-resources block the close method will be called automatically at the end of the block.
		try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext()) {
			
			//Create simple serializable POJO
			Student student = new Student();
			student.setFirstName("Jack");
			student.setLastName("Sparrow");
			student.setRoll(21);
			student.setStandard(12);
			
			//Instead of directly putting the serializable object in send method, here we need to
			//use below to set the filter property
			ObjectMessage objectMessage = jmsContext.createObjectMessage(student);
			//objectMessage.setIntProperty("roll", 21);
			objectMessage.setStringProperty("firstName", "Jack");
			
			//Create producer and send message on the fly
			jmsContext.createProducer().send(queue, objectMessage);
			logger.info("Message sent successfuly by producer");
			
			//Create consumer and receive message on the fly
			//Set message selector/filter as well
			//Student messageReceived = jmsContext.createConsumer(queue, "roll=11").receiveBody(Student.class, 2000);
			//Student messageReceived = jmsContext.createConsumer(queue, "roll BETWEEN 10 AND 20").receiveBody(Student.class, 2000);
			//Student messageReceived = jmsContext.createConsumer(queue, "firstName='Jack'").receiveBody(Student.class, 2000);
			//Student messageReceived = jmsContext.createConsumer(queue, "firstName LIKE 'J%'").receiveBody(Student.class, 2000);
			//Student messageReceived = jmsContext.createConsumer(queue, "firstName IN ('Jack', 'Subhajit')").receiveBody(Student.class, 2000);
			Student messageReceived = jmsContext.createConsumer(queue, "firstName IN ('Subhajit') OR JMSPriority BETWEEN 3 AND 9").receiveBody(Student.class, 2000);
			logger.info("Message received by consumer >>> {}", messageReceived);
		}
	}
}
