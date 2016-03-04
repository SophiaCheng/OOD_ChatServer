package com.chatServer;

import java.util.Date;

public class Message {
	private String content;
	private Date date;
	
	public Message(String content, Date date) {
		 
		this.content = content;
		this.date = date;
	}
	public String getConteng(){
		return content;
	}
	public Date getDate(){
		return date;
	}
}
