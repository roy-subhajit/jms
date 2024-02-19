package com.training.jms;

import com.training.jms.demo.basic.JMS2ContextDemo;
import com.training.jms.demo.basic.JMS2PropertyDemo;
import com.training.jms.demo.basic.JMSQueueConsumer;
import com.training.jms.demo.basic.JMSQueueProducer;
import com.training.jms.demo.basic.JMSTopicConsumer;
import com.training.jms.demo.basic.JMSTopicProducer;
import com.training.jms.demo.correlation.MyJMSAppOne;
import com.training.jms.demo.correlation.MyJMSAppTwo;
import com.training.jms.demo.expiry.MessageExpiryDemo;
import com.training.jms.demo.filter.MessageFilterApp;
import com.training.jms.demo.queue.AsyncEmailSenderApp;
import com.training.jms.demo.queue.DemoAppOne;
import com.training.jms.demo.request.reply.JMSAppOne;
import com.training.jms.demo.request.reply.JMSAppTwo;
import com.training.jms.demo.setjmsreplyto.JMSMyAppOne;
import com.training.jms.demo.setjmsreplyto.JMSMyAppTwo;
import com.training.jms.demo.topic.Harry;
import com.training.jms.demo.topic.Tommy;
import com.training.jms.demo.topic.WeatherChannel;

public class Application {
	public static void main(String[] args) {
		try {
			//JMS Queue demo
			new JMSQueueProducer().produceQueue();
			new JMSQueueConsumer().consumeQueue();

			//JMS Topic demo	
//			new Thread(new JMSTopicConsumer()).start();
//			new JMSTopicProducer().produceTopic();
			
			//Produce some message and use Queue browser just to browse the Queue content and not consume it
//			new JMSProducer().produceQueue();
//			new QueueBrowserDemo().browse();
			
			//JMS 2.0 Demonstration
//			new JMS2ContextDemo().createAndReceiveMessage();
			
			//Demonstrate setting of JMS priority
//			new JMS2MessagePriority().sendReceiveMessage();
			
			//Demonstrate setting of default JMS priority
//			new JMS2MessageDefaultPriority().sendReceiveMessage();
			
			//Show-casing Request-Reply Messaging Pattern
//			new Thread(new JMSAppOne()).start();
//			new Thread(new JMSAppTwo()).start();
			
			//Show-casing JMS Reply To (destination) example
//			new Thread(new JMSMyAppOne()).start();
//			new Thread(new JMSMyAppTwo()).start();
			
			//Show-casing JMS Correlation ID. We used the JMS Message ID to set the Corr. ID
//			new Thread(new MyJMSAppOne()).start();
//			new Thread(new MyJMSAppTwo()).start();
			
			//Demonstrate Expiry time
			//new MessageExpiryDemo().createAndReceiveMessage();
			
			//Show message from Expiry queue after expired time
//			new MessageExpiryDemo().fetchFromExpiryQueue();
			
			//An example of JMS property
//			new JMS2PropertyDemo().createAndReceiveMessage();
			
			//Real Life example of P2P. Show-casing email sender utility in an Asynchronous fashion
//			new Thread(new AsyncEmailSenderApp()).start();
//			new DemoAppOne().sendMessageToQueue();
			
			//Real Life example of Pub-Sub. Show-casing a weather channel broadcast
//			new Thread(new Tommy()).start();
//			new Thread(new Harry()).start();
//			new WeatherChannel().broadcastMessage();
			
			//Demonstrate JMS message filter/selector
//			new MessageFilterApp().createAndReceiveMessage();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
