package com.training.jms.dto;

import java.io.Serializable;

public class Mail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String sender;
	private String subject;
	private String body;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "Mail [sender=" + sender + ", subject=" + subject + ", body=" + body + "]";
	}
}
