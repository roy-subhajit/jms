package com.training.jms.demo.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.training.jms.dto.Mail;

public class EmailSenderListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(EmailSenderListener.class);
	
	@Override
	public void onMessage(Message message) {
		logger.info("<< Starting onMessage >>");
		
		try {
			//Get message object directly as Mail object
			Mail mail = message.getBody(Mail.class);
			
			// Sending email using SMTP server
			logger.info("Sending mail for >>> {}", mail);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("<< Ending onMessage >>");
	}

}
