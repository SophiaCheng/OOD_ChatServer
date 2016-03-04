package com.chatServer;

import java.util.ArrayList;

public abstract class Conversation {
	protected int id;
	protected ArrayList<User> participants;
	protected ArrayList<Message> message;
	
	public int getId(){
		return id;
	}
	public ArrayList<Message> getMessage(){
		return message;
	}
	public boolean addMessage(Message m){
		//TODO;
		return false;
	}
}
